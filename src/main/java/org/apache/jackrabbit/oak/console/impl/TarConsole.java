/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jackrabbit.oak.console.impl;

import java.io.File;
import java.io.IOException;

import org.apache.jackrabbit.oak.plugins.segment.SegmentNodeStore;
import org.apache.jackrabbit.oak.plugins.segment.file.FileStore;

/**
 *
 */
public class TarConsole extends Console {
   private final FileStore fileStore;
   
   public TarConsole(String repoPath){
      super();
      try {
         fileStore = new FileStore(new File(repoPath), 256, false);
         store = new SegmentNodeStore(fileStore); 
         root = store.getRoot();
      } catch (IOException e) {
         throw new RuntimeException("Error while opening the Tar repository",e);
      } 
   }
   
   /* (non-Javadoc)
    * @see org.apache.jackrabbit.oak.console.Console#close()
    */
   @Override
   public void close() {
      fileStore.close();
   }
}
