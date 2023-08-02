/** @type {import('tailwindcss').Config} */

const withMT = require("@material-tailwind/react/utils/withMT");

module.exports = withMT({
  content: ["./index.html", "./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    screens : {
      // 모바일 480까지 / 모바일가로,태블릿 480~767 / 태블릿 가로 768~1024 / 피씨 1024 
      aa: { max:"479px"},
      bb: { min:"480px",max:"767px"},
      cc: { min:"768px",max:"1023px"},
      dd: { min:"1080px"},
      

    },
    extend: {},
  },
  plugins: [],
});
