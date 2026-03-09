export default {
  content: [
    "./index.html",
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        sans: ['Inter', 'system-ui', 'sans-serif'],
      },
      colors: {
        primary: '#0066cc', /* Phoncachxanh Blue Accent */
        secondary: '#000000',
        dark: '#111111',
        dark1: '#222222',
        light: '#ffffff',
        muted: '#9ca3af',
        danger: '#dc2626',
        pcx: {
          blue: '#1d4ed8',
          gray: '#f5f5f5',
          text: '#111111'
        }
      },
    },
  },
  plugins: [],
}
