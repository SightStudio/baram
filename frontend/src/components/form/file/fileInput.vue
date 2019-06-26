<!--
    커스텀 file input 컴포넌트

    @author Dong-Min Seol
    @since  2019.05.24
-->
<template>
    <div class="custom-file-wrapper">
        <div class="file-wrapper">
            <span class="placeholder">{{ placeholder }}</span>
            <label class="file-input">
                검색 <input type="file" :name="name" accept="image/*" capture="camera">
            </label>
        </div> <!-- .filw-wrapper END -->
        <p class="bottom-desc">{{ bottomDesc }}</p>
    </div> <!-- .custom-file-wrapper END -->
</template>
<script>
export default {
    props: {
        name : {
            type: String,
            required: false,
            default() { return '';}
        },
        placeholder : {
            type: String,
            required: false,
            default() { return '';}
        },
        bottomDesc : {
            type: String,
            required: false,
            default() { return '';}
        }
    }
}
</script>
<style scoped>
    .custom-file-wrapper > .file-wrapper { text-align: left; color: #fff; border : 1px solid #cfcfcf; display: inline-block;}
    .custom-file-wrapper > .file-wrapper > .placeholder { padding: 0.5em; float: left; width: 200px; white-space: nowrap; overflow: hidden; color : #000}
    .custom-file-wrapper > .file-wrapper >.file-input { padding: 0.5em; display: inline-block; background: #1297D5; cursor: pointer; }
    .custom-file-wrapper > .file-wrapper > .file-input > input {display: none;}
    .bottom-desc {color: #000;}
</style>

