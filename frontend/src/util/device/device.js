// Import F7
import Framework7 from 'framework7/framework7.esm.bundle.js';

/**
 * 현재 사용자가 접속한 장치를 알아내는 함수
 * 
 * Framework7 라이브러리
 * 
 * @author Dong-Min Seol
 * @since  2019.08.07
 */
export const getDevice = () => {
    if(Framework7.device.ios)
        return 'ios'
    else if(Framework7.device.android)
        return 'android'
    else if(Framework7.device.desktop)
        return 'desktop'
    else if(Framework7.device.cordova)
        return 'cordova'
    else if(Framework7.device.phonegap)
        return 'phonegap'
    else if(Framework7.device.windowsPhone)
        return 'windowsPhone'
    else if(Framework7.device.iphone)
        return 'ios'
    else if(Framework7.device.iphoneX)
        return 'ios'
    else if(Framework7.device.ipod)
        return 'ios'
    else if(Framework7.device.ipad)
        return 'ios'    
}
