// Import Vue
import Vue from 'vue';
import axios from 'axios'

// Import F7
import Framework7, { Dom7 } from 'framework7/framework7.esm.bundle.js';

// Import F7 Vue Plugin
import Framework7Vue from 'framework7-vue/framework7-vue.esm.bundle.js';

// Import F7 Styles
import 'framework7/css/framework7.bundle.css';

// Import Icons and App Custom Styles
import IconsStyles from '{CSS}/icons.css';

// Import App Component
import App from './app.vue';

// load Global CSS
import '@/assets/css/common/reset.css';
import 'filepond/dist/filepond.min.css';
import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css';

import {store} from '{STORE}/store.js'

// Init F7 Vue Plugin
Framework7.use(Framework7Vue)

//axios.defaults.baseURL = 'https://insight-vue.cf/';
// axios.defaults.baseURL = 'http://localhost:8080/';
Vue.prototype.$http = axios;

// Init App
new Vue({
  el         : '#app',
  store      : store,
  template   : '<app/>',
  components : { app: App }
});