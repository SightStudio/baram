import loadScriptOnce   from 'load-script-once'
import { getGPSbyHTML } from '{UTIL}/gps/gps.js'

const DAUM_KAKAO_API_JS_KEY  = 'a4fc4b25bcb5e92ae0531a8e22c6a6d4&libraries'
const DAUM_KAKAO_MAP_LIB_URL = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${DAUM_KAKAO_API_JS_KEY}&libraries=drawing,clusterer,services&autoload=false`

class DaumMap {

    // 생성자
    constructor () { 
      this.map    = null;       // 지도 객체 
      this.places = null;       // 장소 검색 인스턴스
      this.clicked_lat = '';
      this.clicked_lng = '';
      this.markersWithoutCluster;
      Map.initialize()
    }
    
    async mount (elementId, event) {
      await Map.initialize()
        
      // [1] 기존 맵 객체가 있을 경우 재활용
      if (Map.cachedMaps[elementId]) {
        this.map                   = Map.cachedMaps[elementId]
        this.markersWithoutCluster = Map.cachedMapsMarkers[elementId]
        const oldElement = this.map.getNode()
        const newElement = document.getElementById(elementId)
        newElement.parentNode.replaceChild(oldElement, newElement)

      // [2] 기존 맵 객체가 없을 경우 init
      } else {
        // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
        
        this.map = new Map.kakao.maps.Map(
          document.getElementById(elementId),
          {
            center: new Map.kakao.maps.LatLng(37.3399, 126.7346),
            minLevel: 1,
            maxLevel: 9,
            level: 3,
          },
        )

        // 맵 부수 환경 세팅
        let mapTypeControl = new kakao.maps.MapTypeControl();

        this.map.setCopyrightPosition(Map.kakao.maps.CopyrightPosition.BOTTOMLEFT, true)
        this.map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
        this.map.clusters = {}
        this.map.markersWithoutCluster = []
        Map.cachedMaps[elementId]  = this.map
      }

      // [3] clickEvent 적용 [ 미존재시 현재 클릭 위치만 설정]
      kakao.maps.event.addListener(this.map, 'click', (mouseEvent) => {
        var latlng = mouseEvent.latLng;
        this.clicked_lat = latlng.getLat();
        this.clicked_lng = latlng.getLng();
        
        if(event.click) { event.click(); }
      }, {passive: true});// click() end
      
      // [3] dragend 이벤트 적용
      if(event.dragend) {
        kakao.maps.event.addListener(this.map, 'dragend', () => {        
          // 지도 중심좌표
          let latlng = this.map.getCenter(); 
          event.dragend();
        }, {passive: true});
      }// dragend() end

      // [4] zoomChanged 이벤트 적용
      if(event.zoomChanged) {
        kakao.maps.event.addListener(this.map, 'zoom_changed', () => {    
          // 지도 중심좌표
          event.zoomChanged();
        })
      }// zoomChanged() end
      
      // [5] 장소 검색 객체 생성
      this.places = new kakao.maps.services.Places();
      return this;
    }// mount end

    /**
     * 지도에 마커 설정 추가
     * 
     * @author Dong-Min Seol
     * @since  2019.05.25
     */
    addMarkerClusters (clusterSpecs = []) {
      clusterSpecs.forEach(({ key, color, zIndex = 0, singleIconURL = null }) => {
        if (this.map.clusters[key]) return
  
        const cluster = this.map.clusters[key] = new Map.kakao.maps.MarkerClusterer({
          map: this.map,
          averageCenter: true,
          gridSize: 30,
          minClusterSize: 2,
          minLevel: 8,
          disableClickZoom: false,
          calculator: [8, 20, 30], // 0~9, 10~19, 20~ 개수
          styles: [ 
            { width: '30px', height: '30px', background: color, opacity: 0.95, border: '2px solid white', borderRadius: '100%', color: 'white', textAlign: 'center', lineHeight: '27px', fontSize: '20px', fontWeight: 'bold'}, 
            { width: '36px', height: '36px', background: color, opacity: 0.95, border: '2px solid white', borderRadius: '100%', color: 'white', textAlign: 'center', lineHeight: '33px', fontSize: '22px', fontWeight: 'bold'},
            { width: '48px', height: '48px', background: color, opacity: 0.95, border: '2px solid white', borderRadius: '100%', color: 'white', textAlign: 'center', lineHeight: '44px', fontSize: '25px', fontWeight: 'bold'}
          ]
        })

        cluster._icon = new Map.kakao.maps.MarkerImage(
          singleIconURL,
          new Map.kakao.maps.Size(25, 25),
        )
        cluster._zIndex = zIndex
      })
      return this;
    }
  
    /**
     * 지도에 마커 추가 함수 
     * 
     * @author Dong-Min Seol
     * @since  2019.05.25
     */
    addMarkers (markerSpecs = []) {
      const markerSpecsWithoutClusterKey = []
      const markerSpecsByClusterKey = markerSpecs.reduce((result, spec) => {
        if (!spec.clusterKey) {
          markerSpecsWithoutClusterKey.push(spec)
          return result
        }
        if (!result[spec.clusterKey]) {
          result[spec.clusterKey] = []
        }
        result[spec.clusterKey].push(spec)
        return result
      }, {})
      
      markerSpecsWithoutClusterKey.forEach(({ lat, lng, title = null, onClick = null }) => {
        const marker = new Map.kakao.maps.Marker({
          map: this.map,
          position: new Map.kakao.maps.LatLng(lat, lng),
          title,
        })
  
        if (onClick) {
          Map.kakao.maps.event.addListener(marker, 'click', onClick)
        }
  
        this.map.markersWithoutCluster.push(marker)
      }) // markerSpecsWithoutClusterKey loop end
  
      for (let clusterKey in markerSpecsByClusterKey) 
      {
        const cluster = this.map.clusters[clusterKey]
        cluster.addMarkers(
          markerSpecsByClusterKey[clusterKey].map(({ lat, lng, title = null, onClick = null }) => {
            const marker = new Map.kakao.maps.Marker({
              title,
              position: new Map.kakao.maps.LatLng(lat, lng),
              image: cluster._icon,
              zIndex: cluster._zIndex,
            })
  
            if (onClick) {
              Map.kakao.maps.event.addListener(marker, 'click', onClick)
            }
            return marker;
          })
        )
      } // for loop end
    } // addMarkers() END
    
    /**
     * 마커 제거 함수
     * 
     * @author Dong-Min Seol
     * @since  2019.07.01
     */
    clearMarkers () {
      // 클러스터 마커 제거
      for (let k in this.map.clusters) {
        const cluster = this.map.clusters[k]
        cluster.clear()
      }
      // 일반 마커 제거
      this.map.markersWithoutCluster.forEach( marker => {
        marker.setMap(null)
      })
  
      this.map.markersWithoutCluster = []
      //this.map.markersWithoutCluster.length = 0;
    }
    
    /**
     * 지도의 중앙을 세팅하는 함수
     * 
     * @author Dong-Min Seol
     * @since  2019.07.01
     */
    setCenter ({ lat, lng, maxLevel = 8 }) {
      if (this.map.getLevel() > maxLevel) {
        this.map.setLevel(maxLevel)
      }
      this.map.panTo(
        new Map.kakao.maps.LatLng(lat, lng)
      )
    }

    /**
     * 현재 위치로 지도의 중앙을 세팅하는 함수
     * 
     * @author Dong-Min Seol
     * @since  2019.07.01
     */
    setCenterInGPS (){ 
      getGPSbyHTML( gps => {
        let lat = gps.coords.latitude;
        let lng = gps.coords.longitude;
        this.map.panTo(new Map.kakao.maps.LatLng(lat, lng));
      }); 
    }
    /**
     * 위치 검색 함수
     * 
     * @author Dong-Min Seol
     * @since  2019.07.01
     */
    searchArea(keyword, callback) {
      this.places.keywordSearch( keyword, (data, status, pagination) => {
          let result = { REPL_CD : '', REPL_MSG : '', DATA : ''};
          
          if (status === kakao.maps.services.Status.OK) {
            result.REPL_CD  = '000001'
            result.REPL_MSG = '정상'
            result.DATA     = data.slice(0, 5);

          } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
            result.REPL_CD  = '500001'
            result.REPL_MSG = '검색 결과가 존재하지 않습니다.'

          } else if (status === kakao.maps.services.Status.ERROR) {
            result.REPL_CD  = '500002'
            result.REPL_MSG = '검색 결과 중 오류가 발생했습니다.'
          }
          
          callback(result);
      });
    }
    /**
     * 로드뷰 정보 표시 함수
     * 
     * @author Dong-Min Seol
     * @since  2019.07.01
     */
    attachRoadview(){
      this.map.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);    
    }
    /**
     * 로드뷰 정보 제거 함수
     * 
     * @author Dong-Min Seol
     * @since  2019.07.01
     */
    detachRoadview(){
      this.map.removeOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);  
    }

  }
  
  Map.cachedMaps        = {}
  Map.cachedMapsMarkers = {}
  Map.kakao = null
  Map.initialize = function () {
    return new Promise((resolve, reject) => {
      loadScriptOnce(DAUM_KAKAO_MAP_LIB_URL, (err) => {
        if (err) return reject(err)
        Map.kakao = window.kakao
        Map.kakao.maps.load(() => resolve())
      })
    })
  }
  export default DaumMap