/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.dubbo.demo.consumer;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.demo.CallbackService;
import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.demo.GreetingService;

public class Application {
    public static void main(String[] args) {
        // 很多个 reference
        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        reference.setInterface(DemoService.class);
        reference.setCheck(false);

        ReferenceConfig<GreetingService> greetingReference = new ReferenceConfig<>();
        greetingReference.setInterface(GreetingService.class);
        greetingReference.setCheck(false);

        ReferenceConfig<CallbackService> callbackServiceReferenceConfig = new ReferenceConfig<>();
        callbackServiceReferenceConfig.setInterface(CallbackService.class);
        callbackServiceReferenceConfig.setCheck(false);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap
                .application(new ApplicationConfig("dubbo-demo-api-consumer"))
                .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
                .reference(reference)
                .reference(greetingReference)
                .start();

        String message = ReferenceConfigCache.getCache().get(reference).sayHello("dubbo");
        System.out.println(message);

        final GreetingService greetingService = ReferenceConfigCache.getCache().get(greetingReference);
        final String hello = greetingService.hello();

        System.out.println(hello);


    }
}
