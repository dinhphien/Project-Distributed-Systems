package Server;

import java.io.*;

class ThreadedStreamHandler extends Thread {

    InputStream inputStream;
    String adminPassword;
    OutputStream outputStream;
    PrintWriter printWriter;
    StringBuilder outputBuffer = new StringBuilder();
    private boolean sudoIsRequested = false;

    ThreadedStreamHandler(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void run() {

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                outputBuffer.append(line + "\n");
            }
        } catch (IOException ioe) {

            ioe.printStackTrace();
        } catch (Throwable t) {

            t.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {

            }
        }
    }

    private void doSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {

        }
    }

    public StringBuilder getOutputBuffer() {
        return outputBuffer;
    }

}
