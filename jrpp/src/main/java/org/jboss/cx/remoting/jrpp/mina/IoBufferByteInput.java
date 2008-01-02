package org.jboss.cx.remoting.jrpp.mina;

import org.jboss.cx.remoting.spi.protocol.ByteInput;
import org.apache.mina.common.IoBuffer;
import java.io.IOException;

/**
 *
 */
public final class IoBufferByteInput implements ByteInput {
    private final IoBuffer ioBuffer;

    public IoBufferByteInput(final IoBuffer ioBuffer) {
        this.ioBuffer = ioBuffer;
    }

    public int read() throws IOException {
        return ioBuffer.hasRemaining() ? ioBuffer.get() : -1;
    }

    public int read(byte[] data) throws IOException {
        return read(data, 0, data.length);
    }

    public int read(byte[] data, int offs, int len) throws IOException {
        if (! ioBuffer.hasRemaining()) {
            return -1;
        }
        int c = Math.min(ioBuffer.remaining(), len);
        ioBuffer.get(data, offs, c);
        return c;
    }

    public int remaining() {
        return ioBuffer.remaining();
    }

    public void close() throws IOException {
    }
}
