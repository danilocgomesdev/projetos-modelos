import { BrowserRouter } from "react-router-dom";
import "../App/layout/global.css";
import { Router } from "./Routes/Router";
import { SidebarHeader } from "./layout/components/SidebarHeader";

export const App = () => {
  return (
    <BrowserRouter basename="/cr5-web/v2">
      <SidebarHeader>
        <Router />
      </SidebarHeader>
    </BrowserRouter>
  );
};
