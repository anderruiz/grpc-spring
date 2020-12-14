/*
 * Copyright (c) 2016-2020 Michael Zhang <yidongnan@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.devh.boot.grpc.test.config;

import org.springframework.context.annotation.Configuration;

import com.google.protobuf.Empty;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import net.devh.boot.grpc.server.service.exceptionhandling.GrpcExceptionHandler;
import net.devh.boot.grpc.server.service.exceptionhandling.GrpcServiceAdvice;
import net.devh.boot.grpc.test.proto.SomeType;
import net.devh.boot.grpc.test.proto.TestServiceGrpc;

@Configuration
public class GrpcServiceAdviceConfig {


    @GrpcService
    public class TestGrpcService extends TestServiceGrpc.TestServiceImplBase {

        @Override
        public void normal(final Empty request, final StreamObserver<SomeType> responseObserver) {
            throw new IllegalArgumentException("Trigger GrpcServiceAdvice");
        }
    }

    @GrpcServiceAdvice
    public class TestAdvice {

        @GrpcExceptionHandler
        public Status handleIllegalArgumentException(IllegalArgumentException e) {
            return Status.NOT_FOUND.withCause(e).withDescription(e.getMessage());
        }
    }

}
