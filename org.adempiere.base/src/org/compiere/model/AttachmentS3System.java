/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2012 Trek Global                                             *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.compiere.util.CLogger;
import org.compiere.util.Util;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

/**
 * @author rgriepsma
 *
 */
public class AttachmentS3System implements IAttachmentStore {
	
	private final CLogger log = CLogger.getCLogger(getClass());
	
	@Override
	public boolean save(MAttachment attach,MStorageProvider prov) {

		AmazonS3 s3 = getS3Client();
	    		
		String attachmentPathRoot = prov.getURL();
		if (attach.m_items == null || attach.m_items.size() == 0) {
			attach.setBinaryData(null);
			return true;
		}
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.newDocument();
			final Element root = document.createElement("attachments");
			document.appendChild(root);
			document.setXmlStandalone(true);
			// create xml entries
			for (int i = 0; i < attach.m_items.size(); i++) {
				if (log.isLoggable(Level.FINE)) log.fine(attach.m_items.get(i).toString());
			
				File entryFile = null;
				
				try {
					entryFile = attach.m_items.get(i).getFile();
				} catch (Exception ex) {}
				
				if (entryFile != null)
				{
					final String path = entryFile.getAbsolutePath();
	
					if (log.isLoggable(Level.FINE)) log.fine(path + " - " + attachmentPathRoot);
	
					StringBuilder fileLocation = new StringBuilder().append(getAttachmentPathSnippet(attach)).append(File.separator).append(entryFile.getName());
	
					s3.putObject(new PutObjectRequest(attachmentPathRoot, fileLocation.toString(), entryFile));
				}
				
				final Element entry = document.createElement("entry");
				if (attach.getEntryName(i).lastIndexOf("/") > -1)
				{
					entry.setAttribute("name", attach.getEntryName(i).substring(attach.getEntryName(i).lastIndexOf("/")+1));
					entry.setAttribute("file", attach.getEntryName(i));
				} else {
					entry.setAttribute("name", attach.getEntryName(i));					
					entry.setAttribute("file", new StringBuilder().append(getAttachmentPathSnippet(attach)).append(File.separator).append(attach.getEntryName(i)).toString());
				}
				root.appendChild(entry);
			}

			final Source source = new DOMSource(document);
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			final Result result = new StreamResult(bos);
			final Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.transform(source, result);
			final byte[] xmlData = bos.toByteArray();
			if (log.isLoggable(Level.FINE)) log.fine(bos.toString());
			attach.setBinaryData(xmlData);
			attach.setTitle(MAttachment.XML);
			return true;
		} catch (Exception e) {
			log.log(Level.SEVERE, "saveLOBData", e);
		}
		attach.setBinaryData(null);
		return false;

	}
	
	@Override
	public boolean loadLOBData(MAttachment attach,MStorageProvider prov) {

		AmazonS3 s3 = getS3Client();
		
		String attachmentBucket = prov.getURL();
		if (Util.isEmpty(attachmentBucket)) {
			log.severe("No S3 bucket defined -> set bucket name in storageprovider URL");
			return false;
		}

		// Reset
		attach.m_items = new ArrayList<MAttachmentEntry>();
		
		byte[] data = attach.getBinaryData();
		if (data == null)
			return true;
		if (log.isLoggable(Level.FINE)) log.fine("TextFileSize=" + data.length);
		if (data.length == 0)
			return true;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.parse(new ByteArrayInputStream(data));
			final NodeList entries = document.getElementsByTagName("entry");
			for (int i = 0; i < entries.getLength(); i++) {
				final Node entryNode = entries.item(i);
				final NamedNodeMap attributes = entryNode.getAttributes();
				final Node fileNode = attributes.getNamedItem("file");
				final Node nameNode = attributes.getNamedItem("name");
				if(fileNode==null || nameNode==null){
					log.severe("no filename for entry " + i);
					attach.m_items = null;
					return false;
				}
				if (log.isLoggable(Level.FINE)) log.fine("name: " + nameNode.getNodeValue());
				String filePath = fileNode.getNodeValue();
				if (log.isLoggable(Level.FINE)) log.fine("filePath: " + filePath);
				if(filePath!=null){
					filePath = filePath.replaceFirst(attach.ATTACHMENT_FOLDER_PLACEHOLDER, "attachmentPathRoot".replaceAll("\\\\","\\\\\\\\"));
					//just to be sure...
					String replaceSeparator = File.separator;
					if(!replaceSeparator.equals("/")){
						replaceSeparator = "\\\\";
					}
					filePath = filePath.replaceAll("/", replaceSeparator);
					filePath = filePath.replaceAll("\\\\", replaceSeparator);
				}
				if (log.isLoggable(Level.FINE)) log.fine("filePath: " + filePath);
								
				S3Object object = s3.getObject(new GetObjectRequest(attachmentBucket, fileNode.getNodeValue()));
				final byte[] dataEntry = IOUtils.toByteArray(object.getObjectContent());				
				final MAttachmentEntry entry = new MAttachmentEntry(fileNode.getNodeValue(), dataEntry, attach.m_items.size() + 1);
				attach.m_items.add(entry);				
			}

		} catch (SAXException sxe) {
			// Error generated during parsing)
			Exception x = sxe;
			if (sxe.getException() != null)
				x = sxe.getException();
			x.printStackTrace();
			log.severe(x.getMessage());

		} catch (ParserConfigurationException pce) {
			// Parser with specified options can't be built
			pce.printStackTrace();
			log.severe(pce.getMessage());

		} catch (IOException ioe) {
			// I/O error
			ioe.printStackTrace();
			log.severe(ioe.getMessage());
		}

		return true;
	}

	/**
	 * Returns a path snippet, containing client, org, table and record id.
	 * @return String
	 */
	private String getAttachmentPathSnippet(MAttachment attach){		
		StringBuilder msgreturn = new StringBuilder().append(attach.getAD_Client_ID()).append(File.separator)
				.append(attach.getAD_Org_ID()).append(File.separator)
				.append(attach.getAD_Table_ID()).append(File.separator).append(attach.getRecord_ID());
		return msgreturn.toString();
	}

	@Override
	public boolean delete(MAttachment attach, MStorageProvider provider) {
		while (attach.m_items.size() > 0) {
			deleteEntry(attach, provider, attach.m_items.size()-1);
		}
		return true;
	}

	@Override
	public boolean deleteEntry(MAttachment attach, MStorageProvider provider, int index) {
		String attachmentBucket = provider.getURL();
		final MAttachmentEntry entry = attach.m_items.get(index);
		
    	//Try not actually deleting the files from S3 to prevent errors
		//s3.deleteObject(new DeleteObjectRequest(attachmentBucket, entry.getName()));

	    attach.m_items.remove(index);
		attach.save(); // must save here as the operation cannot be rolled back on filesystem
		if (log.isLoggable(Level.CONFIG)) log.config("Index=" + index + " - NewSize=" + attach.m_items.size());
		return true;
	}
	
	private AmazonS3 getS3Client() {
		AWSCredentials credentials = null;
		try {
	        credentials = new ProfileCredentialsProvider().getCredentials();
	    } catch (Exception e) {
			log.log(Level.SEVERE, "Cannot log in to AWS S3, check AWS credentials file in /home/user/.aws/credentials, it should look like: [default]    aws_access_key_id=XXX   aws_secret_access_key=YYY");
	    }
		
		AmazonS3 s3 = new AmazonS3Client(credentials);
	    Region singapore = Region.getRegion(Regions.AP_SOUTHEAST_1);
	    s3.setRegion(singapore);
		return s3;
	}

}
