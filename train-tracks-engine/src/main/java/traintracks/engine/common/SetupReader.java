package traintracks.engine.common;

import java.io.*;

public class SetupReader {
    private final String path;

    public SetupReader(String path) {
        this.path = path;
    }

    public String read(Class baseClass) {

        try {
            InputStream in = baseClass.getResourceAsStream(this.path);
            if (in == null) {
                in = baseClass.getClassLoader().getResourceAsStream(this.path);
            }

            StringWriter writer = new StringWriter();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            lineLoop:
            while (true) {
                StringBuffer buffer = new StringBuffer();
                int character;
                while (true) {
                    character = reader.read();
                    if (character == -1) {
                        if (buffer.length() == 0) {
                            break lineLoop;
                        }
                        break;
                    }
                    buffer.append((char) character);
                    if ((character == (int) '\n') || (character == (int) '\r')) {
                        break;
                    }
                }
                String line = buffer.toString();
                writer.append(line);
            }
            in.close();
            reader.close();
            return writer.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read file at path: " + this.path, e);
        }
    }

}
