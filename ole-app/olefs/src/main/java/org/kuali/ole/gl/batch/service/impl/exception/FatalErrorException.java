/*
 * Copyright 2006 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl2.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ole.gl.batch.service.impl.exception;

/**
 * Fatal error exception that is thrown in GL processing
 * 
 */

public class FatalErrorException extends Exception {

    public FatalErrorException() {
        super();
    }

    public FatalErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public FatalErrorException(String message) {
        super(message);
    }

    public FatalErrorException(Throwable cause) {
        super(cause);
    }

}
