/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//jelly/jelly-tags/xml/src/java/org/apache/commons/jelly/tags/xml/ParseTagSupport.java,v 1.2 2003/01/22 10:56:27 jstrachan Exp $
 * $Revision: 1.2 $
 * $Date: 2003/01/22 10:56:27 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 * 
 * $Id: ParseTagSupport.java,v 1.2 2003/01/22 10:56:27 jstrachan Exp $
 */
package org.apache.commons.jelly.tags.xml;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;

import org.apache.commons.jelly.TagSupport;
import org.apache.commons.jelly.XMLOutput;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.io.SAXContentHandler;
import org.dom4j.io.SAXReader;

/** 
 * An abstract base class for any tag which parsers its body as XML.
 *
 * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
 * @version $Revision: 1.2 $
 */
public abstract class ParseTagSupport extends TagSupport {

    /** The Log to which logging calls will be made. */
    private static final Log log = LogFactory.getLog(ParseTagSupport.class);

    /** The variable that will be generated for the document */
    private String var;
    
    /** The markup text to be parsed */
    private String text;

    /** The SAXReader used to parser the document */
    private SAXReader saxReader;

    public ParseTagSupport() {
    }


    // Properties
    //-------------------------------------------------------------------------                
    /** The variable name that will be used for the Document variable created
     */
    public String getVar() {
        return var;
    }

    /** Sets the variable name that will be used for the Document variable created
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * Returns the text to be parsed
     * @return String
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text to be parsed by this parser
     * @param text The text to be parsed by this parser
     */
    public void setText(String text) {
        this.text = text;
    }

    
    /** @return the SAXReader used for parsing, creating one lazily if need be  */
    public SAXReader getSAXReader() throws Exception {
        if (saxReader == null) {
            saxReader = createSAXReader();
        }
        return saxReader;
    }

    /** Sets the SAXReader used for parsing */
    public void setSAXReader(SAXReader saxReader) {
        this.saxReader = saxReader;
    }
    

    // Implementation methods
    //-------------------------------------------------------------------------                

    /**
     * Factory method to create a new SAXReader
     */    
    protected abstract SAXReader createSAXReader() throws Exception;
    
    
    /**
     * Parses the body of this tag and returns the parsed document
     */
    protected Document parseBody(XMLOutput output) throws Exception {
        SAXContentHandler handler = new SAXContentHandler();
        XMLOutput newOutput = new XMLOutput(handler);
        handler.startDocument();
        invokeBody( newOutput);
        handler.endDocument();
        return handler.getDocument();
    }
    
    /**
     * Parses the give piece of text as being markup
     */
    protected Document parseText(String text) throws Exception {
        if ( log.isDebugEnabled() ) {
            log.debug( "About to parse: " + text );
        }
        return getSAXReader().read( new StringReader( text ) );
    }

    /**
     * Parses the given source
     */    
    protected Document parse(Object source) throws Exception {
        // #### we should allow parsing to output XML events to
        // the output if no var is specified
        
        if (source instanceof String) {
            String uri = (String) source;
            source = context.getResource(uri);
        }
        
        if (source instanceof URL) {
            return getSAXReader().read((URL) source);
        }
        else if (source instanceof File) {
            return getSAXReader().read((File) source);
        }
        else if (source instanceof Reader) {
            return getSAXReader().read((Reader) source);
        }
        else if (source instanceof InputStream) {
            return getSAXReader().read((InputStream) source);
        }
        else {
            throw new IllegalArgumentException(
                "Invalid source argument. Must be a String, Reader, InputStream or URL."
                    + " Was type; "
                    + source.getClass().getName()
                    + " with value: "
                    + source);
        }
    }
}
