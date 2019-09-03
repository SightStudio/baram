<template>
  <f7-page ptr :ptr-mousewheel="true" 
               @page:beforein="loadList" 
               @ptr:refresh="loadList">

               <!-- :infinite-distance="50"
               :infinite-preloader="false" -->
    <f7-appbar>
      <f7-navbar
          title="자유 게시판" 
          back-link="Back">

        <f7-nav-right>
            <f7-button>
              <f7-icon material="search" ></f7-icon>
            </f7-button>
            <f7-link @click="validLoginUser">글 작성</f7-link>
        </f7-nav-right>
      </f7-navbar>
    </f7-appbar>
    
    <br><br>
    <f7-list media-list>
      <f7-list-item
        v-for="(item, index) in items"
        :key="index"
        :title="item.TITLE"
        :subtitle="item.AUTHOR"
        :link="item | toURL" bbs_seq="item.BBS_SEQ" >
        <img class="lazy" slot="media" :src="item.IMG_SRC" width="44">
      </f7-list-item>

      <f7-block-footer>
        <p> 아래로 잡아당겨 새로고침을 할 수 있습니다 </p>
      </f7-block-footer>
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
      toURL(item) {
        return `/community/view/${item.BBS_SEQ}/`
      }
    },
    methods: 
    {
      /**
       * 게시글 데이터를 로딩하는 함수
       */
      loadList() 
      {
        const URI = `api/community/list/${this.pageStart}-${this.pageEnd}`;
        this.$http.get(URI)
                  .then( data => {
                      this.items.length = 0;
                      let list = data.data.response.communityList;
                      list.forEach((el) => { 
                        this.items.push(
                          {
                            BBS_SEQ  : el.BBS_SEQ,
                            TITLE    : el.TITLE,
                            AUTHOR   : el.USER_NAME,
                            IMG_SRC  : el.IMG_SRC != 'no-image' ? `file/${el.IMG_SRC}` : require('@/assets/image/common/no_image.png'),
                            REG_TIME : el.REG_TIME
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