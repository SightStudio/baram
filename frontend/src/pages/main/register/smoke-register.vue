<template>
  <f7-page>
    <f7-block class="smoke-register-wrapper">
      <f7-appbar>

        <div class="left">
          <f7-button color="black" panel-open="left">
            <f7-icon f7="menu"></f7-icon>
          </f7-button>
        </div>

        <p> 위치 등록 </p>

        <div class="right">
          <f7-button color="black" href="/">
            <f7-icon f7="home"></f7-icon>
          </f7-button>
        </div>
      </f7-appbar> <!-- navbar END -->

      
      <f7-block id="map-container">
        <!-- 다음 지도 -->
        <DaumMap 
          :elementId=mapId 
          :height=mapHeight
          :markers="markers"
          @click-map="mapClick"
          ref="dmap">
        </DaumMap>
      </f7-block>
      <br>

      <f7-list class="form-list">
        <radioSwitch :radioList="radioList" ref="regiserType"/>

        <!-- 1. 지명 -->
        <f7-input 
          type="text" 
          label="Name"
          :value="form.title"
          @input="form.title = $event.target.value"
          placeholder="1. 장소명" outline>
        </f7-input>
        <br>

        <!-- 2. 지명 -->
        <f7-list inline-labels no-hairlines>
          <f7-list-input v-model="form.geo_longitude" type="text" label="경도" outline readonly>
            <f7-icon material="place" slot="media"></f7-icon>
          </f7-list-input>
          
          <f7-list-input v-model="form.geo_latitude"  type="text" label="위도" outline readonly>
            <f7-icon material="place" slot="media"></f7-icon>
          </f7-list-input>
        </f7-list>

        <f7-segmented raised>
          <f7-button outline medium @click="setGPSvalue">현위치</f7-button>
          <f7-button outline medium @click="setRegisterType">지도에서 찾기</f7-button>
        </f7-segmented>

        <f7-input 
          type="textarea"
          :value="form.content"
          @input="form.content = $event.target.value"
          placeholder="장소에 대한 설명을 써주세요"></f7-input>
      </f7-list> <!-- .form-list END -->

      <f7-block>
          <!-- 3. 사진 -->
          <file-pond
            name="img"
            ref="pond"
            label-idle="이곳을 눌러서 파일을 올려주세요"
            :server="{process: tempSaveImg, revert: revertTempImgSave}"
            allow-multiple="true"
            accepted-file-types="image/jpeg, image/png, image/gif"
          />
      </f7-block>
      <f7-button outline large @click="registerLocation"> 등록하기</f7-button>
    </f7-block> <!-- .smoke-register-wrapper END -->
  </f7-page> 
</template>

<script>
import DaumMap          from '{COMPONENT}/map/map.vue'
import radioSwitch      from '{COMPONENT}/form/switch/switch-radio.vue'
import { getGPSbyHTML } from '{UTIL}/gps/gps.js'

import vueFilePond, { setOptions }     from 'vue-filepond';
import FilePondPluginFileValidateType  from 'filepond-plugin-file-validate-type';
import FilePondPluginImagePreview      from 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.esm.js';

const FilePond = vueFilePond(FilePondPluginFileValidateType, FilePondPluginImagePreview );

export default { 
  components: {
    DaumMap,
    radioSwitch,
    FilePond
  },
  data () {
    return {
      mapId      : 'register-map',
      mapHeight  : '200px',
      radioList  : [ 
        { desc : '금연구역', name : 'isSmoke', val : 'non-smoke'}, 
        { desc : '흡연구역', name : 'isSmoke', val : 'smoke'}
      ],
      markers: [],
      form : {
        title         : '',
        geo_longitude : '',
        geo_latitude  : '',
        applType      : '',
        content       : '',
        imgList   : []
      },
    }
  },
  methods : {
    setGPSvalue() {
      let formData = this.form;
      let markers  = this.markers;

      getGPSbyHTML( gps =>  {
        let map = this.$refs.dmap.map
        let lat = gps.coords.latitude;
        let lng = gps.coords.longitude;
        
        formData.geo_longitude = lng
        formData.geo_latitude  = lat

        // 지도 ceneter 설정
        map.setCenter( {lat : lat, lng: lng } );
        map.clearMarkers();

        // 현 위치에 마커 설정
        markers.splice(0, markers.length);
        markers.push({ name: 'sight', type: 'smoke', location: {lat : lat, lng: lng } } );
      })
    }, // setGPSvalue() END

    /**
     * 지도 클릭시 클릭한 위치의 좌표를 마커로 표시 및 data()에 바인딩
     * 
     * @author Dong-Min Seol
     * @since  2019.08.16
     */
    mapClick(map) {
      let formData = this.form;
      map.clearMarkers();
      this.markers.splice(0, this.markers.length);
      this.markers.push({ name: 'sight', type: 'smoke', location: { lat: map.clicked_lat, lng: map.clicked_lng }} );

      formData.geo_longitude = map.clicked_lng;
      formData.geo_latitude  = map.clicked_lat;
    }, // mapClick(map) END
    /**
     * 지도 마커 등록 함수
     * 
     * @author Dong-Min Seol
     * @since  2019.08.16
     */
    registerLocation() {

      let data = new URLSearchParams();
      data.append('type'       , this.$refs.regiserType.value)
      data.append('title'      , this.form.title);
      data.append('latitude'   , this.form.geo_latitude);
      data.append('longitude'  , this.form.geo_longitude);
      data.append('content'    , this.form.content);
      data.append('imgListStr' , this.form.registerImg);
      data.append('USER_JWT'   , this.$store.state.userJwt);

      this.$http.post('api/user-smoke/register', data)
                .then( (res) => {
                    const response = res.data.response;
                    let REPL_MSG = response.REPL_CD == '000000' ? '위치 등록에 성공하였습니다.' :
																                                  '위치 등록에 실패하였습니다.'
							
                    this.$f7.dialog.alert(REPL_MSG, '위치 등록', () => {
                      if(response.REPL_CD == '000000') {
                        this.$f7router.navigate('/gmap', { clearPreviousHistory : true })
                      }
                    })
                })
    }, // registerLocation() END

    setRegisterType() {
      console.log(this.$refs.regiserType.value)
    },
    // ############  file-pond options START  ##############
    tempSaveImg (fieldName, file, metadata, load, error, progress, abort) {

      const formData = new FormData();
      formData.append(fieldName, file, file.name);

      const request = new XMLHttpRequest();
      request.open('POST', 'api/user-smoke/temp/img');

      request.upload.onprogress = (e) => progress(e.lengthComputable, e.loaded, e.total);

      request.onload = () => {
          if( request.status >= 200 && request.status < 300) {
              load(request.responseText)
              const data = JSON.parse(request.responseText);
              imgList.push(data.response.fileName);
          } else {
              error('oh no');
          }
      };

      request.send(formData);
      return { abort: () => { request.abort(); abort();}};
    },
    revertTempImgSave(uniqueFileId, load, error) {
        const data    = JSON.parse(uniqueFileId);
        const imgName = data.response.fileName;

        let index = imgList.indexOf(imgName);

        if (index !== -1) 
          imgList.splice(index, 1);

        load();
      }
    }
    // ############  file-pond options END  ##############
}
</script>

<style scoped>
#map-container { width: 100%; height: 70%; padding: 0;}
.smoke-register-wrapper {width: 100%; margin-bottom: 30%; padding : 0;}
.smoke-register-wrapper .logo { width: 90%; height: 100px; text-align: center; font-size: 0; margin-top: 30px;}
.smoke-register-wrapper .logo img{ width: 100%; height:100px; display: inline-block;}
</style>