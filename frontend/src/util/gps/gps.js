/**
 * HTML API - 현재 위치를 가져오는 함수
 * 
 * @author Dong-Min Seol
 * @since  2019.08.07
 */
export const getGPSbyHTML = (callback) => {
    if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(callback)
    } else {
        alert('해당 브라우저는 GPS를 지원하지 않습니다.')
    }
}

/**
 * HTML API - 현재 위치를 가져오는 함수
 * 
 * @author Dong-Min Seol
 * @since  2019.08.07
 */
export let watchPosition =  (callback) => {
    if(navigator.geolocation) {
        navigator.geolocation.watchPosition(callback);
    } else {
        alert('해당 브라우저는 GPS를 지원하지 않습니다.')
    }
}

/**
 * HTML API - 위의 함수에서 할당한 watch position을 해제하는 함수
 * 
 * @author Dong-Min Seol
 * @since  2019.08.07
 */
export const freeWatchPosition = (watchPosition) => {
    navigator.geolocation.clearWatch(watchPosition)
}

export const getGPSbyCordova = () => {
    navigator.geolocation.getCurrentPosition (
    function(position) {
        console.log(position)
        data.position = position;
    },
    function(error) {
        data.error = error;
    }, 
    { maximumAge: 3000, timeout: 5000, enableHighAccuracy: true });
}