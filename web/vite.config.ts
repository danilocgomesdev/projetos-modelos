import legacy from "@vitejs/plugin-legacy";
import react from "@vitejs/plugin-react";
import { defineConfig, loadEnv } from "vite";

export default function ({ command, mode }) {
  const env = loadEnv(mode, process.cwd(), "");

  console.log("--------------------------------------------------------------------------");
  console.log(`Building for ${mode} with ${env.VITE_BASE} as base. Path prod: ${env.PROD}`);
  console.log("--------------------------------------------------------------------------");

  return defineConfig({
    base: `${env.VITE_BASE}`,
    plugins: [
      legacy({
        targets: ["firefox >= 52", "safari >= 10", "edge >= 14", "chrome >= 39", "ie 11"],
        ignoreBrowserslistConfig: true,
        renderLegacyChunks: false,
        modernPolyfills: ["es/global-this", "es/object"],
      }),
      react(),
    ],
    build: {
      target: "es2015",
    },
    server: {
      port: Number(process.env.VITE_SERVER_PORT) || 3000,
    },
    define: {
      "process.env.NODE_ENV": `"${mode}"`,
      "process.env.APP_VERSION": JSON.stringify(process.env.npm_package_version),
    },
  });
}
