<!--

-->
<template>
  <f7-page>
      <f7-row no-gap>
        <f7-col width="15">
          <f7-button color="black" panel-open="left">
            <f7-icon f7="menu"></f7-icon>
          </f7-button>
        </f7-col>

        <f7-col width="70">
          <f7-searchbar
            placeholder="위치 검색"
            :disable-button="false"
            :clear-button="false"
            :custom-search="true"
            :no-shadow="false"
          ></f7-searchbar>
        </f7-col>

        <f7-col width="15">
          <f7-button color="black">
            <f7-icon f7="home"></f7-icon>
          </f7-button>
        </f7-col>
      </f7-row> <!-- navbar END -->
    
    <f7-block id="map-container">

      <!-- 다음 지도 -->
      <DaumMap 
        :elementId=mapId
        :height=mapHeight> 
      </DaumMap>

    </f7-block>

    <f7-block class="location-info-wrapper">
      <f7-toolbar class="location-info-header">
        <radioSwitch
          :radioList="radioList.isSmoke">
        </radioSwitch>

        <radioSwitch 
          :radioList="radioList.isOfficial">
        </radioSwitch>

        <f7-button
            small
            outline
            color="gray"
            class="location-notice" 
            text="* 더보기" 
            sheet-open=".location-info-sheet">
        </f7-button>        
      </f7-toolbar>  <!-- .location-info-header END -->

      <f7-list no-hairlines-between>
            <f7-list-item link="#" header="약50미터" title="산기대 G동 흡연장" after="자세히">
              <f7-icon slot="media" icon="demo-list-icon"></f7-icon>
            </f7-list-item>

            <f7-list-item link="#" header="약600미터" title="산기대 TIP 흡연장" after="자세히">
              <f7-icon slot="media" icon="demo-list-icon"></f7-icon>
            </f7-list-item>
        </f7-list>
    </f7-block> <!-- .location-info-wrapper END -->

    <f7-sheet
      class="location-info-sheet"
      swipe-to-close
      closeByOutsideClick
      :opened="infoListOpened"
      @sheet:closed="infoListOpened = false"
    >
      <f7-page-content>
        <f7-button fill color="gray" @click="infoListOpened = false" style="opacity:0.5;">
          <f7-icon material="line_weight"></f7-icon>
        </f7-button>
        
        <f7-chip class="location-notice" text="* 장소 정보"></f7-chip>
        <f7-block class="location-info">
          <p>흠냐</p>
          <p>흠냐</p>
          <p>흠냐</p>
          <p>흠냐</p>
        </f7-block>
      </f7-page-content>
    </f7-sheet> <!-- .location-info-sheet END -->
  </f7-page>
</template>

<script>
import { getGPSPosition } from '{UTIL}/gps/gps.js'
import DaumMap            from '{COMPONENT}/map/map.vue'
import radioSwitch        from '{COMPONENT}/form/switch/switch-radio.vue'
export default {
  components : {
    DaumMap,
    radioSwitch
  },
  data() {
    return {
      mapId     : 'map',
      mapHeight : '100%;',
      radioList : {
        isSmoke    : [
          { desc : '금연구역', name : 'isSmoke', val : 'smoke' },
          { desc : '흡연구역', name : 'isSmoke', val : 'nonSmoke' }
        ],
        isOfficial : [
          { desc : '공식',   name : 'isOfficial', val : 'official' },
          { desc : '제보함', name : 'isOfficial', val : 'nonOfficial' }
        ]
      },
      infoListOpened: false,
    }
  },
  methods : {
    initCss : () => {
      let root = document.documentElement;
      root.style.setProperty('--f7-navbar-bg-color', 'tracurrentStylensparent');
      root.style.setProperty('--f7-bars-bg-color',   'transparent');
      root.style.setProperty('--f7-theme-bg-color',  'transparent');
    },

    /**
     * 다음 지도 초기화 함수
     * 
     * @author Dong-Min Seol
     * @since  2019.05.11
     */
    initMap : () => {
        let gps  = {};
        // getGPSPosition(gps);
      
        const container = document.getElementById('map'); 			  // 지도를 담을 영역의 DOM 레퍼런스
        const options = { 											                  // 지도를 생성할 때 필요한 기본 옵션
          center: new daum.maps.LatLng(37.3402849, 126.7313189),  // 지도의 중심좌표.
          level: 6											                          // 지도의 레벨(확대, 축소 정도)
        };

        const map = new daum.maps.Map(container, options); 			  // 지도 생성 및 객체 리턴

        // 지도에 마커 표시
        const marker = new daum.maps.Marker({
            map: map, 
            position: new daum.maps.LatLng(37.3402849, 126.7313189)
        });
    },

    /**
     * axios 테스트
     * 
     * @author Dong-Min Seol
     * @since  2019.05.11
     */
    axios_test : function () {
      this.$http.get('api/index.do')
       .then ( (data) => {
        // console.log(JSON.stringify(data))
        // console.log('sight-log')
      })
    }
  },
  created() {
    this.initCss();
    this.axios_test();
    // this.initMap()
  }
}
</script>

<style scoped>
#map-container { width: 100%; height: 70%; padding: 0;}
#map-container .map-btn-layer{ width: 100%; height: 10%; position:absolute; bottom: 70px; z-index: 9999; }
#map-container .map-btn-layer .map-type-wrapper { position: absolute; top:100%; right:0; text-align: right; }
#map-container #map { width: 100%; height: 100%;}

.gnb { background-color : transparent; text-align: right; }
.location-info-wrapper { margin-top: -33px; margin: 0; padding: 0;}
.location-info-wrapper .location-info-header { margin-bottom: 15px; margin: 0; padding: 0;}

.location-info-sheet {height:50%; --f7-sheet-bg-color: #fff !important;}
.location-info-sheet .location-info{border: 2px solid blue; margin: 10px 0}
</style>
