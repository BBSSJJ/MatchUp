/** @type {import('next').NextConfig} */
require('dotenv').config();

const nextConfig = {
  images: {
    domains: [
      'ddragon.leagueoflegends.com',
    ]
  },
  output: "standalone"
};



export default nextConfig;
