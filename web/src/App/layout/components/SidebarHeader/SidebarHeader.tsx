import { Box, Drawer, DrawerContent, useColorModeValue, useDisclosure } from "@chakra-ui/react";
import { RouteProps } from "react-router-dom";
import { useSidebarStore } from "../../../stores/SidebarHeaderStore";
import NavigationHeader from "../NavigationHeader";
import { MobileNav } from "./MobileNav";
import { SidebarContent } from "./SidebarContent";

export function SidebarHeader({ children }: RouteProps) {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const { isMenuOpen } = useSidebarStore();

  return (
    <Box h="100vh" bg={useColorModeValue("light.background", "dark.background")}>
      <SidebarContent onClose={() => onClose} display={{ base: "none", md: "block" }} />
      <Drawer
        autoFocus={false}
        isOpen={isOpen}
        placement="left"
        onClose={onClose}
        returnFocusOnClose={false}
        onOverlayClick={onClose}
        size="full"
      >
        <DrawerContent overflow={"auto"}>
          <SidebarContent onClose={onClose} />
        </DrawerContent>
      </Drawer>
      <MobileNav onOpens={onOpen} />
      <Box ml={{ base: 0, md: isMenuOpen ? 60 : 20 }} p="2" transition="1s ease">
        <NavigationHeader />
        <Box
          bg={useColorModeValue("light.text", "dark.primary")}
          borderRadius="0.5rem"
          flex="1"
          m="1rem"
          p={2}
        >
          {children}
        </Box>
      </Box>
    </Box>
  );
}
