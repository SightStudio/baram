<template>
  <div :id="elementId" 
       :style="{ width, height }"
  ><!-- daum kakao map -->
  </div>
</template>

<script>
import DaumMap from '{UTIL}/daummap/daummap.js'
export default {
  props: {
    markers    : { type: Array,  required: false, default(){ return [] }},   // 흡연 / 금연구역 마커   
    myLocation : { type: Array,  required: false, default(){ return [] }},   // 현재 위치 마커
    elementId  : { type: String, required: true },                           // 지도 ELEMENT ID
    width      : { type: String, required: false, default: '100%' },         // CSS width
    height     : { type: String, required: false, default: '640px'},         // CSS height
    level      : { type: Number, required: false, default: 3 },              // 지도 zoom level
  },
  data() { 
    return { 
      map    : null,
      latlng : { click_lat: '', click_lng: ''},
    }
  },
  watch: {
    markers: {
      handler () {
        if (typeof window === 'undefined') return // SSR

          this.initMap(this.markers)
      },
      immediate: true
    },
  },
  methods: {
    /**
     *  지도 초기화 함수
     */
    async initMap (markers) {

      // [a] 맵이 없을 경우 지도 인스턴스 새로 생성 
      if (!this.map) {
        let event = {
          click       : () => this.$emit('click-map'  ), 
          dragend     : () => this.$emit('dragend'),
          zoomChanged : () => this.$emit('zoom-changed')
        }
        const map = new DaumMap()
        await map.mount(this.elementId, event)

        map.addMarkerClusters([
          { key: 'smoke'     , color: '#222529', zIndex: 0, singleIconURL: require('{IMG}/gmap/markerSmoke.png')},
          { key: 'non-smoke' , color: '#209cee', zIndex: 1, singleIconURL: require('{IMG}/gmap/markerNonSmoke.png')},
          { key: 'myPosition', color: '#1907c0', zIndex: 2, singleIconURL: require('{IMG}/gmap/my_location.png')},
        ])
        this.map = map
        
        // [3] 현재 위치를 기준으로 지도 위치 설정
        this.map.setCenterInGPS();

        // 렌더링 끝난 이후 이벤트 세팅
        // this.$emit('after-render');
      } else {
        // [b] 기존 맵이 존재 할 경우 마커만 초기화하고 재사용
        this.map.clearMarkers()
      }
        // [2] 초기화 마커 init  - [1] 흡연 / 금연구역 마커
        this.map.addMarkers(
          markers.map(
            (marker) => {
              const { name, type, location: { lat, lng }, data } = marker
              return { lat, lng, clusterKey: type, title: name, data: data, onClick: () => { this.$emit('click-marker', marker) }}
            }
          )
        )

        // [2] 초기화 마커 init  - [2] 현재 내 위치 마커
        this.map.addMarkers(
          this.myLocation.map(
            (marker) => {
              const { name, type, location: { lat, lng } } = marker
              return { lat, lng, clusterKey: type, title: name }
            }
          )
        )
      
    }, // end initMap()
  }
}
</script>