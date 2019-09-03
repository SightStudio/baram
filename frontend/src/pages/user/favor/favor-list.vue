<template>
    <f7-page>
        <f7-appbar>
            <div class="left">
                <f7-button color="black" back>
                    <f7-icon material="arrow_back"></f7-icon>
                </f7-button>
            </div>

            <div class="left">
                <p> 즐겨찾기 목록 </p>
            </div>

            <div class="right">
                <f7-button color="black" href="/">
                    <f7-icon f7="home"></f7-icon>
                </f7-button>
            </div>
        </f7-appbar> <!-- navbar END -->

        <f7-list media-list>
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
            <f7-list-item
                link="#"
                title="Don't Stop Me Now"
                after="$22"
                subtitle="Queen"
                text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla sagittis tellus ut turpis condimentum, ut dignissim lacus tincidunt. Cras dolor metus, ultrices condimentum sodales sit amet, pharetra sodales eros. Phasellus vel felis tellus. Mauris rutrum ligula nec dapibus feugiat. In vel dui laoreet, commodo augue id, pulvinar lacus."
            >
                <img slot="media" src="https://cdn.framework7.io/placeholder/people-160x160-2.jpg" width="80" />
            </f7-list-item>
            <f7-list-item
                link="#"
                title="Billie Jean"
                after="$16"
                subtitle="Michael Jackson"
                text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla sagittis tellus ut turpis condimentum, ut dignissim lacus tincidunt. Cras dolor metus, ultrices condimentum sodales sit amet, pharetra sodales eros. Phasellus vel felis tellus. Mauris rutrum ligula nec dapibus feugiat. In vel dui laoreet, commodo augue id, pulvinar lacus."
            >
                <img slot="media" src="https://cdn.framework7.io/placeholder/people-160x160-3.jpg" width="80" />
            </f7-list-item>
        </f7-list>
    </f7-page>
</template>

<script>
import logo from '{COMPONENT}/logo/logo.vue'
export default { 
  components : {},
}
</script>

<style scoped>
    .singup-wrapper { width: 100%; height:100%; }
    .singup-wrapper .btn-facebook{background-color: #3b5998; }
    .singup-wrapper .btn-kakao   {background-color: #f7e600; color: brown !important;}
</style>