/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//jelly/src/java/org/apache/commons/jelly/Tag.java,v 1.14 2003/01/26 11:03:36 morgand Exp $
 * $Revision: 1.14 $
 * $Date: 2003/01/26 11:03:36 $
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
 * $Id: Tag.java,v 1.14 2003/01/26 11:03:36 morgand Exp $
 */

package org.apache.commons.jelly;

/** 
 * <p><code>Tag</code> represents a Jelly custom tag.
 * A Tag is only ever used by a single thread so that Tag developers do not
 * need to concern themselves with mutli-threading issues when writing a Tag.
 * A Tag is created per custom tag in a script, per invocation.
 * So there is no need to worry about pooling errors like those caused
 * in JSP 1.x.(</p>
 *
 * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
 * @version $Revision: 1.14 $
 */
public interface Tag {

    /** 
     * @return the parent of this tag
     */
    public Tag getParent();

    /** 
     * Sets the parent of this tag
     */
    public void setParent(Tag parent);

    /** 
     * @return the body of the tag 
     */
    public Script getBody();

    /** 
     * Sets the body of the tag
     */
    public void setBody(Script body);
    
    /** 
     * Gets the context in which the tag will be run
     */
    public JellyContext getContext();

    /** 
     * Sets the context in which the tag will be run 
     */
    public void setContext(JellyContext context) throws JellyTagException;

    /** 
     * Evaluates this tag after all the tags properties have been initialized.
     */
    public void doTag(XMLOutput output) throws MissingAttributeException, JellyTagException;

    /**
     * A helper method to invoke this tags body
     */
    public void invokeBody(XMLOutput output) throws JellyTagException;
    
}
