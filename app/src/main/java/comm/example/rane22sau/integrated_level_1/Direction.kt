package comm.example.rane22sau.integrated_level_1

class Direction {
//    var string: String? = null
    var img_size = 0
    var x_face = 0f
    var y_face = 0f
    fun getDirection(x: Float, y: Float, w: Float, h: Float): String {
        img_size = 720 * 1280 / 5
        return if (x <= 720 / 4) {
            if (w.toInt() * h / 5 > 0.03 * img_size) {
                "1"
            } else if (w.toInt() * h / 5 < 0.01 * img_size) {
                "7"
            } else {
                "4"
            }
        } else if ((720 / 4) * 3 <= x) {
            if (w.toInt() * h / 5 > 0.03 * img_size) {
                "3"
            } else if (w.toInt() * h / 5 < 0.01 * img_size) {
                "9"
            } else {
                "6"
            }
        } else {
            if (w.toInt() * h / 5 > 0.03 * img_size) {
                "2"
            } else if (w.toInt() * h / 5 < 0.01 * img_size) {
                "8"
            } else {
                "5"
            }
        }
    }


}