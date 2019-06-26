export const getGPSPosition = () => {
    navigator.geolocation.getCurrentPosition(
        function(position){
            console.log(position)
            data.position = position;
        }, 
        function(error){
            data.error = error;
        }, 
        { maximumAge: 3000, timeout: 5000, enableHighAccuracy: true });
}