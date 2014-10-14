<?php
  
    class PNGSerializer {
    
        private function __construct(){ }
        
        public static function encodeText($message = null) {
            $bytes = null;
            try {
                $message = utf8_encode($message);
                $chars = str_split($message);
                if(count($chars) > 0){
                    $bytes = array();
                    for($i=0; $i<count($chars); $i++){
                        $code = unpack('C*', $chars[$i])[1];
                        array_push($bytes, $code);
                    }
                }
            } catch (Exception $e) {
                $bytes = null;
            }
            return $bytes;
        }
        
        public static function decodeText($bytes = null) {
            $message = null;
            try {
                if(count($bytes) >0){
                    $message = "";
                    for($i=0; $i<count($bytes); $i++) {
                        $message .= pack('C*', $bytes[$i]);
                    }
                    $message = utf8_decode($message);
                }
            } catch (Exception $e) {
                $message = null;
            }
            return $message;
        }
        
        public static function encodeBinary($bytes = null) {
            $bmp = null;
            try {
                $sqrt = intval(ceil(sqrt(count($bytes))));
                $bmp = imagecreatetruecolor($sqrt,$sqrt);
                $black = imagecolorallocate($bmp, 0, 0, 0);
                imagecolortransparent($bmp, $black);
                for ($w = 0, $index = 0; $w < $sqrt; $w++){
                    for ($h = 0; $h < $sqrt; $h++){
                        if ($index < count($bytes)){
                            $code = intval($bytes[$index]);
                            $color = imagecolorallocatealpha($bmp, 0, 0, $code, 0);
                            imagesetpixel($bmp, $w, $h, $color);
                        }
                        else{
                            $color = imagecolorallocatealpha($bmp, 0,0,0, 127);
                            imagesetpixel($bmp, $w, $h, $color);
                        }
                        $index++;
                    }
                }
            } catch (Exception $e) {
                $bmp = null;
            }
            return $bmp;
        }
        
        public static function decodeBinary($bmp = null) {
            $bytes = null;
            try {
                $wSize = imagesx($bmp); $hSize = imagesy($bmp);
                for ($w = 0; $w < $wSize; $w++) {
                    for ($h = 0; $h < $hSize; $h++) {
                        if($bytes == null) $bytes = array();
                        $color = imagecolorat($bmp, $w, $h);
                        $info = imagecolorsforindex($bmp, $color);
                        $red = intval($info["red"]);
                        $green = intval($info["green"]);
                        $blue = intval($info["blue"]);
                        $alpha = intval($info["alpha"]);
                        if($alpha == 0) array_push($bytes, $blue);
                        else break;
                    }
                }
            } catch (Exception $e) {
                $bytes = null;
            }
            return $bytes;
        }
        
        public static function loadImage($filename = null){
            return @imagecreatefrompng($filename);
        }
        
        public static function saveImage($bmp = null, $filename = null){
            @imagepng($bmp, $filename, 9);
        }
        
        public static function printImage($bmp = null){
            header('Content-Type: image/png');
            @imagepng($png);
        }
        
        public static function doIt() {
            
            $str = "X5O!P%@AP[4\\PZX54(P^)7CC)7}\$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!\$H+H*";
            $rawdata = PNGSerializer::encodeText($str);
            $png = PNGSerializer::encodeBinary($rawdata);
            PNGSerializer::saveImage($png, "eicar.png");
            imagedestroy($png);
            
            unset($png);
            unset($str);
            unset($rawdata);
            
            $png = PNGSerializer::loadImage("eicar.png");
            $rawdata = PNGSerializer::decodeBinary($png);
            $text = PNGSerializer::decodeText($rawdata);
            unset($png);
            unset($rawdata);
            
            echo $text;
        }
        
    }
    
    PNGSerializer::doIt();

?>