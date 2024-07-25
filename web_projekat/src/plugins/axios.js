"use strict";

import Vue from 'vue';
import axios from "axios";
import router from "@/router";

// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

let config = {
    baseURL: 'http://localhost:8081'
  // timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
};

const _axios = axios.create(config);

_axios.interceptors.request.use(
  function(config) {
    const jwt = localStorage.getItem('jwt')
      config.headers['Authorization'] = `Bearer ` + jwt;
    return config;
  },
  function(error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

// Add a response interceptor
_axios.interceptors.response.use(
    function(response) {
        return response;
    },
    function(error) {
        if (error && error.response && error.response.status === 401) {
            console.warn("Unauthorized request, redirecting to login.");
            router.push({ name: 'Login' });
        }
        return Promise.reject(error);
    }
)

// eslint-disable-next-line no-unused-vars
Plugin.install = function(Vue, options) {
  Vue.axios = _axios;
  window.axios = _axios;
  Object.defineProperties(Vue.prototype, {
    axios: {
      get() {
        return _axios;
      }
    },
    $axios: {
      get() {
        return _axios;
      }
    },
  });
};

Vue.use(Plugin)

export default Plugin;
