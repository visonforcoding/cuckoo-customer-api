package com.vison.wonfu.configure;

import org.apache.commons.io.IOUtils;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * A class to provide the ability to read a {@link javax.servlet.ServletRequest}'s body multiple times.
 * via https://stackoverflow.com/a/36619972/2257038 and https://stackoverflow.com/a/30748533/2257038
 */
public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
    private ByteArrayOutputStream cachedBytes;

    private byte[] cachedBody;

    /**
     * Construct a new multi-read wrapper.
     *
     * @param request to wrap around
     */
    public MultiReadHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        InputStream requestInputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (cachedBytes == null) cacheInputStream();

        return new CachedServletInputStream(cachedBytes.toByteArray());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    private void cacheInputStream() throws IOException {
        /* Cache the inputstream in order to read it multiple times. For
         * convenience, I use apache.commons IOUtils
         */
        cachedBytes = new ByteArrayOutputStream();
        IOUtils.copy(super.getInputStream(), cachedBytes);
    }

    /* An inputstream which reads the cached request body */
    private static class CachedServletInputStream extends ServletInputStream {
        private final ByteArrayInputStream buffer;

        public CachedServletInputStream(byte[] contents) {
            this.buffer = new ByteArrayInputStream(contents);
        }

        @Override
        public int read() throws IOException {
            return buffer.read();
        }

        @Override
        public boolean isFinished() {
            return buffer.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new RuntimeException("Not implemented");
        }
    }
}
