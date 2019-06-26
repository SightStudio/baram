// Import Vue
import Vue from 'vue';
import axios from 'axios'

// Import F7
import Framework7 from 'framework7/framework7.esm.bundle.js';

// Import F7 Vue Plugin
import Framework7Vue from 'framework7-vue/framework7-vue.esm.bundle.js';

// Import F7 Styles
import 'framework7/css/framework7.bundle.css';

// Import Icons and App Custom Styles
import IconsStyles from '{CSS}/icons.css';

// Import App Component
import App from './app.vue';

// loca Global CSS
import '@/assets/css/common/reset.css';

// Init F7 Vue Plugin
Framework7.use(Framework7Vue)

Vue.prototype.$http = axios;

// Init App
new Vue({
  el: '#app',
  template: '<app/>',

  // Register App Component
  components: {
    app: App
  }
});