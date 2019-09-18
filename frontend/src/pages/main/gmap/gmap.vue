<template>
  <f7-page @page:afterin="pageAfterIn">
    <f7-appbar>
      <div class="left">
        <f7-button color="black" panel-open="left">
          <f7-icon f7="menu"></f7-icon>
        </f7-button>
      </div>

      <f7-searchbar
        style="width:100%;"
        inline
        custom-search
        placeholder="위치 검색"
        searchbar:clear
        :disable-button="false"
        :clear-button="true"
        :backdrop="false"
        :value="searchWord"
        @change="searchArea"
        @input="searchWord = $event.target.value"
        @click:clear="clearSearchList"
      ></f7-searchbar>
      
      <div class="right">
        <f7-button href="/" color="black back">
          <f7-icon f7="home"></f7-icon>
        </f7-button>
      </div>
    </f7-appbar> <!-- navbar END -->
    
    <!-- [검색] 검색 목록 리스트 -->
    <f7-list style="position:relative; z-index:9999" >
        <f7-list-item 
            v-for="(search, idx) in searchList" 
            v-bind:key="idx"
            :title="search.place_name"
            @click="gotoSearchArea(search.x, search.y)">
        </f7-list-item>
    </f7-list>
    
    <!-- 다음 지도 -->
    <f7-block id="map-container">
      <DaumMap 
        elementId="map"
        height="100%;"
        :markers="markers"
        :myLocation="positionList"
        @dragend="loadSmokeMarker"
        @zoom-changed="loadSmokeMarker"
        @click-marker="showMarkerInfo"
        ref="dmap">
      </DaumMap>
    </f7-block>

    <!-- [지도 하단] -->
    <f7-block class="location-info-wrapper">
      <f7-toolbar class="location-info-header">

        <!-- [라디오 버튼] 흡연 / 금연 구억 & 공식 비공식 -->
        <radioSwitch :radioList="radioList.isSmoke"    ref="smokeType"    @radio:change="loadSmokeMarker" ></radioSwitch>
        <radioSwitch :radioList="radioList.isOfficial" ref="officialType" @radio:change="loadSmokeMarker" ></radioSwitch>

        <!-- [버튼 1] 로드뷰 표시 -->
        <f7-button @click="toggleRoadview" :color="roadViewVisibleColor">
          <f7-icon material="directions_car"></f7-icon>
        </f7-button>

        <!-- [버튼 2] 현재 위치 추적 -->
        <f7-button @click="traceGPS" :color="watchBtnColor">
          <f7-icon material="my_location"></f7-icon>
        </f7-button>
      </f7-toolbar> <!-- .location-info-header END -->

      <!-- 구역 미리보기 -->
      <f7-list>
        <!-- this.$refs.officialType.value -->
        <f7-list-item  v-if="areaData.officialType == 'official'"
          :title="areaData.AREA_NAME"
          sheet-open=".location-sheet"
          @click="clearRegisterImages"
          link='#'>
        </f7-list-item>

        <f7-list-item v-else-if="areaData.officialType == 'non-official'"
          :title="areaData.AREA_NAME"
          sheet-open=".location-sheet"
          @click="getRegisterImages"
          link='#'>

          <img slot="media" :src="`${areaData.IMG_SRC}`" width="80" />
        </f7-list-item>
      </f7-list>
    </f7-block> <!-- .location-info-wrapper END -->

    <!-- INFO POPUP[Official 정보시트] START  -->
    <f7-sheet class="location-sheet"
              ref="locationInfoSheet"
              swipe-to-close
              :closeByOutsideClick="false"
              :opened="isInfoListOpened"
              @sheet:closed="isInfoListOpened = false">
      <f7-page-content>
        <f7-button fill color="gray" @click="isInfoListOpened = false" style="opacity:0.5;">
          <f7-icon material="line_weight"></f7-icon>
        </f7-button>
        
        <f7-navbar :title="areaData.AREA_NAME"></f7-navbar>
        <br>

        <f7-block class="location-info" v-if="areaData.officialType == 'official'">
          <p>관할 지역   : {{ areaData.MANAGE_AGENCY }}</p>
          <p>관리 기관   : {{ areaData.AGENCY_NAME   }}</p>
          <p>적용 법률   : {{ areaData.RESTRICT_BY   }}</p>
          <p>도로명 주소 : {{ areaData.DORO_ADDRESS  }}</p>
          <p>지역 구분   : {{ areaData.AREA_TYPE     }}</p>
          <p>구역 범위   : {{ areaData.AREA_DETAIL   }}</p>
          <p>과테료      : {{ areaData.PENALTY_PRICE }}</p>
          <p>신고 접수   : {{ areaData.REPORT_CALL   }}</p>
        </f7-block>

        <f7-block class="location-info" v-else-if="areaData.officialType == 'non-official'">
          
          <f7-row >
            <f7-col>
              <p>작성자      : {{ areaData.USER_ID   }} </p>
              <p>작성자 등급 : {{ areaData.RANK_NAME }} </p>
              <p>작성 시간   : {{ areaData.REG_TIME }}  </p>
              <p>설명</p>       
              <textarea cols="60" rows="5" readonly v-model="areaData.CONTENT">
              </textarea>
            </f7-col>

            <f7-col class="text-align-right">
              <f7-gauge
                border-color    ="#2196f3"
                value-text-color="#2196f3"
                label-text-color="#2196f3"
                type="circle"
                :value="0.5"
                :value-text="`${0.5 * 100}%`"
                :size="70"
                :border-width="10"
                :value-font-size="11"
                :label-font-size="11"
                label-text="좋아요"
              ></f7-gauge>
              
              <f7-gauge
                border-color    ="#f32121"
                value-text-color="#f32121"
                label-text-color="#f32121"
                type="circle"
                :value="0.5"
                :value-text="`${0.5 * 100}%`"
                :size="70"
                :border-width="10"
                :value-font-size="11"
                :label-font-size="11"
                label-text="싫어요"
              ></f7-gauge>
            </f7-col>
          </f7-row>
          <br>
          <f7-row class="img-row">
            <f7-swiper :param="{spaceBetween: 0, slidesPerView: 3}" style="height: 100%; width: 100%;">
              <f7-swiper-slide v-for="(imgURL, idx) in areaImgList" v-bind:key="idx" style="width:40%;">
                <f7-link @click="$refs.imgViewer.open()" style="width: 100%; height: 100%;">
                  <img :src="`${imgURL}`" style="height: 100%;">
                </f7-link>
              </f7-swiper-slide>
            </f7-swiper>
          </f7-row>

          <f7-photo-browser
            ref="imgViewer"
            theme="dark"
            back-link-text="돌아가기"
            :photos="areaImgList"
            @photobrowser:open="$refs.locationInfoSheet.close()"
            @photobrowser:close="$refs.locationInfoSheet.open()"
          ></f7-photo-browser>
          
        </f7-block>

        <f7-button 
          fill large
          popover-open=".roadview-popover"
          @click="roadviewInit">
          로드뷰 확인하기
        </f7-button>
        <br>
  
        <f7-segmented raised tag="p">
          <f7-button fill large>즐겨찾기에 추가하기</f7-button>
          <f7-button fill large>댓글 보기</f7-button>
        </f7-segmented>

      </f7-page-content>
    </f7-sheet> 
    <!-- INFO POPUP [official 정보 시트] END -->
    
    <!-- ROADVIEW POPUP START -->
    <f7-popover class="roadview-popover">
      <RoadView
        elementId="nav-roadview"
        ref="rv"
      >
      </RoadView>
    </f7-popover>
    <!-- ROADVIEW POPUP END -->
  </f7-page>
</template>

<script>
import DaumMap       from '{COMPONENT}/map/map.vue'
import RoadView      from '{COMPONENT}/map/road-view.vue'
import radioSwitch   from '{COMPONENT}/form/switch/switch-radio.vue'
import { getGPSbyHTML, watchPosition, freeWatchPosition} from '{UTIL}/gps/gps.js'

export default {
  components : {
    DaumMap,
    radioSwitch,
    RoadView
  },
  data() {
    return {
      searchWord  : '',                    // [위치 검색]     위치 검색어
      searchList  : [],                    // [위치 검색]     위치 검색 목록
      markers     : [],                    // [구역 조회] 마커 데이터
      radioList   : {                      // [구역 조회] 검색 조건 radio 버튼
        isSmoke     : [ {desc: '금연구역', name: 'isSmoke', val: 'nonSmoke'},
                        {desc: '흡연구역', name: 'isSmoke', val: 'smoke'} ],

        isOfficial  : [ {desc: '공식'   , name: 'isOfficial', val: 'official'},
                        {desc: '제보함' , name: 'isOfficial', val: 'nonOfficial'} ]
      },
      isWatching           : false,        // [현재 위치 추적] GPS 트래킹 여부 [토글]
      positionList         : [],           // [현재 위치 추적] GPS 현위치 주소
      watchPointObj        : null,         // [현재 위치 추적] watch position object,
      watchBtnColor        : 'black',      // [현재 위치 추적] 상태 버튼 색
      isRoadViewVisible    : false,        // [로드뷰 정보 표시] 표시 여부
      roadViewVisibleColor : 'black',      // [로드뷰 정보 표시] 상태버튼 색
      isRoadViewOpened     : true,         // [ 로드뷰 팝업 ]
      isInfoListOpened     : false,
      areaData             : {},           // [공식, 비공식] 구역 정보,
      areaImgList          : [],           // [비공식] 첨부파일 정보
      officialData         : {             
          AREA_DETAIL   : '',
          AGENCY_NAME   : '',
          AREA_SIZE     : '',
          AREA_TYPE     : '',
          CITY_NAME     : '',
          DORO_ADDRESS  : '',
          ZIBUN_ADDRESS : '',
          GU_NAME       : '',
          LATITUDE      : '',
          LONGITUDE     : '',
          MANAGE_AGENCY : '',
          NON_SMOKE_NAME: '',
          PENALTY_PRICE : 0,
          NON_SMOKE_SEQ : 0,
          REG_TIME      : '',
          REPORT_CALL   : '',
          RESTRICT_BY   : '',
      },
      customData : {

      },
      infoContainer  : [],
    }
  },
  methods : {
    /**
     * 지도 페이지 진입시 검증 이벤트
     * 
     */
    pageAfterIn() {
      const LAT  = this.$f7route.params.lat;
      const LNG  = this.$f7route.params.lng;
      const TYPE = this.$f7route.params.type;
      const SMOKE_RADIO    = this.$refs.smokeType;
      const OFFICIAL_RADIO = this.$refs.officialType;

      if(LAT && LNG && TYPE) 
      {
        let map = this.$refs.dmap.map
        map.setCenter({lat: LAT, lng : LNG,});

        SMOKE_RADIO.changeValue(TYPE);
        OFFICIAL_RADIO.changeValue('non-official');
      }
      this.loadSmokeMarker();
    },
    /**
     * 금연구역정보 가져오는 함수
     * @author Dong-Min Seol
     * @since  2019.08.07
     */
    loadSmokeMarker() {
      // if(this.$refs.dmap.map.map == null) return;
      // [1] 지도 객체 및 지도의 남서 ~ 북서 위도 경도 가져오기
      const map    = this.$refs.dmap.map.map;
      const bounds = map.getBounds();

      const sw_lat = bounds.getSouthWest().getLat();
      const sw_lng = bounds.getSouthWest().getLng();    
      const ne_lat = bounds.getNorthEast().getLat();
      const ne_lng = bounds.getNorthEast().getLng();

      // [2] 검색 조건 가져오기
      const somkeType    = this.$refs.smokeType.value    == 'smoke'    ? 'smoke'    : 'non-smoke';
      const officialType = this.$refs.officialType.value == 'official' ? 'official' : 'non-official';
      const URI          = `api/map/${officialType}/${sw_lat}&${sw_lng}&${ne_lat}&${ne_lng}/${somkeType}`

      // [3] 지도 레벨이 4 이하일 때, 마커를 로딩함
      if(map.getLevel() <= 4) {
        const self = this;
        self.markers.splice(0, self.markers.length);
        this.$http.get(URI)
                  .then( data => {
                      let list = data.data.response.smokeList;
                      list.forEach((el) => {
                        self.markers.push(
                          { name : 'sight',  type : somkeType , location: { lat : el.LATITUDE, lng : el.LONGITUDE },  data : el }
                        )
                      })
                  })
                  .catch(error => console.log(error))
                  
      } // if map.getLevel() end
    }, // loadSmokeMarker() end

    /**
     * 검색 목록 초기화 함수
     * @author Dong-Min Seol
     * @since  2019.08.07
     */
    clearSearchList() { this.searchList  = [];},

    /**
     * 위치 검색 함수
     * @author Dong-Min Seol
     * @since  2019.08.07
     */
    searchArea() {
      this.searchList  = [];
      let map = this.$refs.dmap.map
      map.searchArea(this.searchWord, (result) => {
          result.DATA.forEach( el => { this.searchList.push(el)} )
      });
    }, // searchArea() end

    /**
     * 지도를 해당 위치로 이동시키는 함수
     * @author Dong-Min Seol
     * @since  2019.08.07
     */
    gotoSearchArea(x, y) {
      this.searchList  = [];
      let map = this.$refs.dmap.map
      map.setCenter({lng : x, lat: y});
      this.loadSmokeMarker()
    },

    /**
     * 현재 위치를 추적하는 함수
     * @author Dong-Min Seol
     * @since  2019.08.07
     */
    traceGPS() {
      this.setGPSvalue()

      this.isWatching = !this.isWatching;
      this.positionList.length = 0;
      let pList  = this.positionList;

      if(this.isWatching) {
      // [1] 위치 추적 허용
        let wp = watchPosition( gps =>  {
          let map = this.$refs.dmap.map
          let lat = gps.coords.latitude;
          let lng = gps.coords.longitude;

          // 지도 ceneter 설정
          map.setCenter( {lat : lat, lng: lng });
          pList.push({ name: 'sight', type: 'myPosition', location: {lat : lat, lng: lng } } );
        })

        this.watchPointObj = wp;
        this.watchBtnColor = 'blue'
        this.$f7.dialog.alert('위치추적이 설정되었습니다.', '위치추적');
      } else {
        // [2] 위치 추적 비허용
        freeWatchPosition(this.watchPointObj)
        this.watchBtnColor = 'black'
        this.$f7.dialog.alert('위치추적이 해제되었습니다.', '위치추적');
      }
    }, // traceGPS() END

    setGPSvalue() {
      this.positionList.length = 0;
      let pList  = this.positionList;

      getGPSbyHTML( gps => {
        let map = this.$refs.dmap.map
        let lat = gps.coords.latitude;
        let lng = gps.coords.longitude;

        // 지도 ceneter 설정
        map.setCenter( {lat : lat, lng: lng } );

        // 현 위치에 마커 설정
        pList.splice(0, pList.length);
        pList.push({ name: 'sight', type: 'myPosition', location: {lat : lat, lng: lng } } );
      })
    }, // setGPSvalue() end
    /**
     * 지도에 로드뷰를 표시해주는 함수
     * @author Dong-Min Seol
     * @since  2019.08.07
     */
    toggleRoadview() {
      this.isRoadViewVisible = !this.isRoadViewVisible;
      let map = this.$refs.dmap.map

      this.isRoadViewVisible    ? map.attachRoadview() : map.detachRoadview()
      this.roadViewVisibleColor = this.isRoadViewVisible? 'blue' : 'black'
    },
    /**
     * 로드 뷰 실행시 위치를 알려주는 함수
     */
    roadviewInit() {
      const roadViewObj = this.$refs.rv.rv;
      roadViewObj.setPanoramaID(this.areaData.LATITUDE, this.areaData.LONGITUDE);
    },
    /**
     * 마커의 상세 정보를 Sheet를 통해 보여주는 함수
     * @author Dong-Min Seol
     * @since  2019.08.07
     */
    showMarkerInfo(marker) {
      marker.data.officialType = this.$refs.officialType.value == 'official' ? 'official' : 'non-official';;
      this.areaData = marker.data;
    },
    /**
     * 사용자 등록 위치 조회시 등록한 사진을 가져오는 함수
     * @author Dong-Min Seol
     * @since  2019.08.07
     */
    getRegisterImages(){ },
    clearRegisterImages() {
      this.areaImgList.length = 0;
    }
  }
}
</script>

<style scoped>
.searchbar{ border: 0 !important; box-shadow: none !important; }

#map-container { width: 100%; height: 70%; padding: 0; }
#map-container .map-btn-layer{ width: 100%; height: 10%; position:absolute; bottom: 70px; }
#map-container .map-btn-layer .map-type-wrapper { position: absolute; top:100%; right:0; text-align: right; }
#map-container #map { width: 100%; height: 100%; }

.gnb { background-color : transparent; text-align: right; }
.location-info-wrapper { margin-top: -33px; margin: 0; padding: 0; }
.location-info-wrapper .location-info-header { margin-bottom: 15px; margin: 0; padding: 0; }

.location-sheet { height:90%; --f7-sheet-bg-color: #fff !important; }
.location-sheet .location-info { height: 60%; }
.location-sheet .location-info .img-row { height: 40%; }
.roadview-popover{ position : absolute; width : 90%; height :90%; top: 50% !important; left: 50% !important; transform: translate(-50%, -50%);}
#nav-roadview{width:100%; height: 100%; z-index: 9999; position: relative;}
</style>
loadSmokeMarker