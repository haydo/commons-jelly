/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//jelly/jelly-tags/beanshell/src/java/org/apache/commons/jelly/tags/beanshell/JellyInterpreter.java,v 1.1 2003/01/11 13:27:29 dion Exp $
 * $Revision: 1.1 $
 * $Date: 2003/01/11 13:27:29 $
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
 * $Id: JellyInterpreter.java,v 1.1 2003/01/11 13:27:29 dion Exp $
 */
package org.apache.commons.jelly.tags.beanshell;

import bsh.EvalError;
import bsh.Interpreter;

import java.util.Iterator;

import org.apache.commons.jelly.JellyContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** Integrates BeanShell's interpreter with Jelly's JellyContext
  *
  * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
  * @version $Revision: 1.1 $
  */
public class JellyInterpreter extends Interpreter {

    /** The Log to which logging calls will be made. */
    private static final Log log = LogFactory.getLog( JellyInterpreter.class );

    private JellyContext context;
    
    public JellyInterpreter() {
    }

    public JellyContext getJellyContext() {
        return context;
    }
    
    public void setJellyContext(JellyContext context) throws EvalError {
        this.context = context;
        
        // now pass in all the variables
        for ( Iterator iter = context.getVariableNames(); iter.hasNext(); ) {
            String name = (String) iter.next();
            Object value = context.getVariable(name);
            name = convertVariableName(name);
            if (name != null) {
                set( name, value );
            }
        }
        
        // lets pass in the Jelly context 
        set( "context", context );
    }

/*
  
    // the following code doesn't work - it seems that
    // all variables must be passed into the Interpreter
    // via set() method
 
    public Object get(String name) throws EvalError {
        if ( context != null ) {
            Object answer = context.getVariable( name );
            if ( answer != null ) { 
                return answer;
            }
        }
        return super.get( name );
    }
*/

    /**
     * Converts variables to a beanshell allowable format or hides names that 
     * can't be converted, by returning null.
     * For now lets just turn '.' into '_'
     */
    protected String convertVariableName(String name) {
        return name.replace('.', '_');
    }
}