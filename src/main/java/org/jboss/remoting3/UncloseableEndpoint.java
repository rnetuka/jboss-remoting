/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2016, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.remoting3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

import javax.net.ssl.SSLContext;

import org.jboss.remoting3.spi.ConnectionProviderFactory;
import org.wildfly.common.Assert;
import org.wildfly.security.auth.client.AuthenticationConfiguration;
import org.wildfly.security.auth.client.AuthenticationContext;
import org.xnio.IoFuture;
import org.xnio.OptionMap;
import org.xnio.XnioWorker;

final class UncloseableEndpoint implements Endpoint {
    private final Endpoint endpoint;

    UncloseableEndpoint(final Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public String getName() {
        return endpoint.getName();
    }

    public Registration registerService(final String serviceType, final OpenListener openListener, final OptionMap optionMap) throws ServiceRegistrationException {
        return endpoint.registerService(serviceType, openListener, optionMap);
    }

    public IoFuture<Connection> getConnection(final URI destination, final SSLContext sslContext, final AuthenticationConfiguration connectionConfiguration, final AuthenticationConfiguration operateConfiguration) {
        return endpoint.getConnection(destination, sslContext, connectionConfiguration, operateConfiguration);
    }

    public IoFuture<Connection> connect(final URI destination, final OptionMap connectOptions) throws IOException {
        return endpoint.connect(destination, connectOptions);
    }

    public IoFuture<Connection> connect(final URI destination, final OptionMap connectOptions, final AuthenticationContext authenticationContext) throws IOException {
        return endpoint.connect(destination, connectOptions, authenticationContext);
    }

    public IoFuture<Connection> connect(URI destination, InetSocketAddress bindAddress, OptionMap connectOptions, AuthenticationContext authenticationContext) throws IOException {
        return endpoint.connect(destination, bindAddress, connectOptions, authenticationContext);
    }

    public Registration addConnectionProvider(final String uriScheme, final ConnectionProviderFactory providerFactory, final OptionMap optionMap) throws DuplicateRegistrationException, IOException {
        return endpoint.addConnectionProvider(uriScheme, providerFactory, optionMap);
    }

    public <T> T getConnectionProviderInterface(final String uriScheme, final Class<T> expectedType) throws UnknownURISchemeException, ClassCastException {
        return endpoint.getConnectionProviderInterface(uriScheme, expectedType);
    }

    public boolean isValidUriScheme(final String uriScheme) {
        return endpoint.isValidUriScheme(uriScheme);
    }

    public XnioWorker getXnioWorker() {
        return endpoint.getXnioWorker();
    }

    public Key addCloseHandler(final CloseHandler<? super Endpoint> handler) {
        return endpoint.addCloseHandler((endpoint, ex) -> handler.handleClose(this, ex));
    }

    public Attachments getAttachments() {
        return endpoint.getAttachments();
    }

    public void awaitClosedUninterruptibly() {
        endpoint.awaitClosedUninterruptibly();
    }

    public void awaitClosed() throws InterruptedException {
        endpoint.awaitClosed();
    }

    public void close() throws IOException {
        throw Assert.unsupported();
    }

    public void closeAsync() {
        throw Assert.unsupported();
    }
}
