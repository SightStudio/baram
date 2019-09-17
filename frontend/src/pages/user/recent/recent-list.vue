<template>
  <f7-page ptr :ptr-mousewheel="true" 
               @page:beforein="loadList" 
               @ptr:refresh="loadList">

               <!-- :infinite-distance="50"
               :infinite-preloader="false" -->
    <f7-appbar>
      <f7-navbar
          title="최근 등록된 구역" 
          back-link="Back">
        <f7-nav-right></f7-nav-right>
      </f7-navbar>
    </f7-appbar>
    <br>
<!-- 
<f7-list-item
  swipeout
  link="#"
  title="Yellow Submarine"
  after="$15"
  subtitle="Beatles"
  text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla sagittis tellus ut turpis condimentum, ut dignissim lacus tincidunt. Cras dolor metus, ultrices condimentum sodales sit amet, pharetra sodales eros. Phasellus vel felis tellus. Mauris rutrum ligula nec dapibus feugiat. In vel dui laoreet, commodo augue id, pulvinar lacus."
>
  <img slot="media" src="https://cdn.framework7.io/placeholder/people-160x160-1.jpg" width="80" />

  <f7-swipeout-actions right>
      <f7-swipeout-button delete confirm-text="Are you sure you want to delete this item?">Delete</f7-swipeout-button>
  </f7-swipeout-actions>
</f7-list-item>
 -->

    <f7-list media-list>
      <f7-list-item
        v-for="(item, index) in items"
        swipeout
        :key="index"
        :title="item.TITLE"
        :subtitle="item.AUTHOR"
        after="보러가기"
        :link="{ LAT : item.LATITUDE, LNG: item.LONGITUDE, TYPE : item.TYPE } | toURL">
        <img slot="media" :src="item.IMG_SRC" width="44">

      </f7-list-item>
    </f7-list>
  </f7-page>
</template>
<script>
  export default {
    components: { },
    data() {
      return {
        items: [ 
          // { TITLE   : '', AUTHOR  : '', IMG_SRC : '', REG_TIME: '' },
        ],
        pageStart : 0,
        pageEnd   : 10,
      };
    },
    filters : {
      toURL(item) { return `/gmap/${item.LAT}&${item.LNG}/${item.TYPE}`}
    },
    methods: 
    {
      /**
       * 게시글 데이터를 로딩하는 함수
       */
      loadList() 
      {
        const URI = `api/user-smoke/recent/${this.pageStart}-${this.pageEnd}`;
        this.$http.get(URI)
                  .then( data => {
                      this.items.length = 0;
                      let list = data.data.response.recentSmokeList;
                      list.forEach((el) => { 
                        this.items.push(
                          {
                            SMOKE_SEQ : el.SMOKE_SEQ,
                            AUTHOR    : el.USER_ID,
                            LATITUDE  : el.LATITUDE,
                            LONGITUDE : el.LONGITUDE,
                            TITLE     : el.TITLE,
                            TYPE      : el.TYPE,
                            IMG_SRC   : el.IMG_SRC != 'no-image' ? `${el.IMG_SRC}` : require('@/assets/image/common/no_image.png'),
                            REG_TIME  : el.REG_TIME
                          }
                        )
                      })
                  })
                  .catch(error => console.log(error))

      }, // loadList() end
      /**
       * 로그인 한 유저만 글을 작성할 수 있게 하는 메소드
       */
      validLoginUser() {
        const userToken = this.$store.state.userJwt;
        if(userToken == null) {
          this.$f7.dialog.alert('로그인을 해야 글 작성이 가능합니다', '알림');
        } else {
          this.$f7router.navigate('/community/write');
        }
      }, // validLoginUser() end 
      moveToPage(){

      },
      loadMore(event, done) {
        // const self = this;
        // setTimeout(() => {
        //   const picURL = `https://cdn.framework7.io/placeholder/abstract-88x88-${(Math.floor(Math.random() * 10) + 1)}.jpg`;
        //   const song   = self.songs[Math.floor(Math.random() * self.songs.length)];
        //   const author = self.authors[Math.floor(Math.random() * self.authors.length)];

        //   self.items.push({
        //     title: song,
        //     author,
        //     cover: picURL,
        //   });
        //   done();
        // }, 1000);
      }, // LoadMore() end 
    },
  };
</script>