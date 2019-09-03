import loadScriptOnce   from 'load-script-once'

const DAUM_KAKAO_API_JS_KEY  = 'a4fc4b25bcb5e92ae0531a8e22c6a6d4&libraries'
const DAUM_KAKAO_MAP_LIB_URL = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${DAUM_KAKAO_API_JS_KEY}&libraries=drawing,clusterer,services&autoload=false`

class RoadView {

    // 생성자
    constructor () { 
      this.roadView       = null;       // 로드뷰 객체 
      this.roadViewClient = null;
      RoadView.initialize()
    }
    
    async mount (elementId, latlng) {
      await RoadView.initialize()
        
      // [1] 기존 로드뷰 객체가 있을 경우 재활용
      if (RoadView.cachedRoadMaps[elementId]) {
        
        this.roadView       = RoadView.cachedRoadMaps[elementId]
        this.roadViewClient = RoadView.cachedRoadMapClients[elementId]
        //[2] 캐싱한 엘리먼트 재사용
        const oldElement = RoadView.cachedRoadMapNode[elementId]
        const newElement = document.getElementById(elementId)
        newElement.parentNode.replaceChild(oldElement, newElement)

        this.setPanoramaID(latlng.lat, latlng.lng)
        // let position = new kakao.maps.LatLng(latlng.lat, latlng.lng);
        // this.roadViewClient.getNearestPanoId(position, 100, (panoId) => { this.roadView.setPanoId(panoId, position); });
      } else {
         // [2] 기존 로드뷰 객체가 없을 경우 init
        let roadView       = new kakao.maps.Roadview(document.getElementById(elementId)) ;
        let roadViewClient = new kakao.maps.RoadviewClient(); 

        this.roadView       = roadView;
        this.roadViewClient = roadViewClient

        // 캐시에 저장
        RoadView.cachedRoadMaps[elementId]       = roadView
        RoadView.cachedRoadMapClients[elementId] = roadViewClient
        RoadView.cachedRoadMapNode[elementId]    = document.getElementById(elementId).cloneNode(true);
      }
      return this;
    } // mount end
    
    /**
     * 파노라마 ID를 변경하는 함수 (로드뷰 이동)
     */
    setPanoramaID(latitude, longitude) {
      let position = new kakao.maps.LatLng(latitude, longitude);
      let rv = this.roadView;

      this.roadViewClient.getNearestPanoId(position, 300, (panoId) => {
        rv.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
      });
    }
  }
  
  RoadView.cachedRoadMaps = {}
  RoadView.cachedRoadMapClients = {}
  RoadView.cachedRoadMapNode = {}
  RoadView.kakao = null
  RoadView.initialize = function () {
    return new Promise((resolve, reject) => {
      loadScriptOnce(DAUM_KAKAO_MAP_LIB_URL, (err) => {
        if (err) return reject(err)
        RoadView.kakao = window.kakao
        RoadView.kakao.maps.load(() => resolve())
      })
    })
  }
  export default RoadView