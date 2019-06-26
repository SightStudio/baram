module.exports = {
  "presets": [
    ["@babel/preset-env", {
      "modules": false,
      "targets": {
        "browsers": ["> 1%", "last 2 versions", "not ie <= 8"]
      }
    }]
  ],
  "plugins": [
    "transform-vue-jsx",
    ["@babel/plugin-transform-runtime",
      {
        "absoluteRuntime": false,
        "corejs": false,
        "helpers": true,
        "regenerator": true,
        "useESModules": false
      }
    ],
    "@babel/plugin-syntax-dynamic-import",  
    [ "module-resolver",
      {
        "alias": {
          "{COMPONENT}" :  "./src/components",
          "{PAGE}"      :  "./src/pages",
          "{ROUTE}"     :  "./src/router",
          "{UTIL}"      :  "./src/util",
          "{CSS}"       :  "./src/assets/css",
          "{IMG}"       :  "./src/assets/image",
          "{FONT}"      :  "./src/assets/font",
        }
      }
    ]
  ]
};
