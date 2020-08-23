package comm.example.rane22sau.integrated_level_1;


public class Direction {
    public String string;
    public int img_size;
    float x_face, y_face;

    public String getDirection(float x, float y, float w, float h) {
        img_size = (int) 720 * 1280 / 5;
        if (x <= 720 / 4) {

            if ((int) w * h / 5 > 0.03 * img_size) {
                return ("1");
            } else if ((int) w * h / 5 < 0.01 * img_size) {
                return ("7");
            } else {
                return ("4");
            }
        } else if ((int) (720 / 4) * 3 <= x) {
            if ((int) w * h / 5 > 0.03 * img_size) {
                return ("3");

            } else if ((int) w * h / 5 < 0.01 * img_size) {
                return ("9");
            } else {
                return ("6");
            }
        } else {
            if ((int) w * h / 5 > 0.03 * img_size) {
                return ("2");
            } else if ((int) w * h / 5 < 0.01 * img_size) {
                return ("8");
            } else {
                return ("5");
            }
        }
    }

    public void print(String string2) {
        /*if (MainActivity.mBluetoothAdapter.isEnabled()) {
            boolean isMessageSent = true;
            try {
                OutputStream os = MainActivity.mBluetoothSocket.getOutputStream();
                os.write(string2.getBytes());
            } catch (IOException e) {
                isMessageSent = false;
                e.printStackTrace();
            }

        } else {
            Log.d("Tag","hello");
        }*/
    }


}