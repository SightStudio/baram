<template>
  <div :id="elementId">
  </div>
</template>

<script>
import RoadView from '{UTIL}/daummap/roadview.js'
export default {
    props: {
        elementId  : { type: String, required: true },
        initLatLng : { type: Object, required: false, default() { return { lat : 33.450701, lng : 126.570667 } } },
    },
    data() {
        return { 
            rv : null,
        }      
    },
    mounted(){
        this.initRoadView(this.initLatLng.lat, this.initLatLng.lng)
    },
    methods: {
        async initRoadView (latitude, longitude) {
            console.log(this.rv)
            if (this.rv == null) {
                const rv = new RoadView()
                await rv.mount(this.elementId, {lat : latitude, lng : longitude});
                this.rv = rv;
            }
        }
    },

}
</script>