import {
  Box,
  BoxProps,
  CloseButton,
  Flex,
  Image,
  useBreakpointValue,
  useColorModeValue,
  useMediaQuery,
} from "@chakra-ui/react";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import logoFieg from "../../../../assets/fieg.svg";
import logoIel from "../../../../assets/iel.svg";
import logoSenai from "../../../../assets/senai.svg";
import logoSesi from "../../../../assets/sesi.svg";
import { usePrimaryColor } from "../../../hooks/usePrimaryColor";
import { useSidebarStore } from "../../../stores/SidebarHeaderStore";
import { useUnidadeStore } from "../../../stores/UnidadeStore";
import { MenuList } from "../Menus";
import { NavItem } from "./NavItem";

interface SidebarProps extends BoxProps {
  onClose: () => void;
}

export const SidebarContent = ({ onClose, ...rest }: SidebarProps) => {
  const logoSize = useBreakpointValue({ base: "12rem", md: "12rem" });
  const { isMenuOpen, setMenuOpen } = useSidebarStore();
  const navigate = useNavigate();
  const unidadeAtual = useUnidadeStore((s) => s.unidadeAtual);
  const [isMobile] = useMediaQuery("(max-width: 759px)");
  const [isTablet] = useMediaQuery("(min-width: 760px) and (max-width: 959px)");
  const [isDesktop] = useMediaQuery("(min-width: 960px)");

  useEffect(() => {
    if (isMobile || isDesktop) {
      setMenuOpen(true);
    } else if (isTablet) {
      setMenuOpen(false);
    }
  }, [isMobile, isTablet, isDesktop]);

  const renderLogo = () => {
    switch (unidadeAtual.entidade) {
      case "FIEG":
        return <Image src={logoFieg} boxSize={logoSize} pt="2" flex="0 0 auto" />;
      case "IEL":
        return <Image src={logoIel} boxSize={logoSize} pt="2" flex="0 0 auto" />;
      case "SENAI":
        return <Image src={logoSenai} boxSize={logoSize} pt="2" flex="0 0 auto" />;
      case "SESI":
        return <Image src={logoSesi} boxSize={logoSize} pt="2" flex="0 0 auto" />;
      default:
        return null;
    }
  };

  return (
    <Box
      transition="1s ease"
      bg={usePrimaryColor()}
      borderRight="1px"
      borderRightColor={useColorModeValue("light.border", "dark.border")}
      w={{ base: "full", md: isMenuOpen ? 60 : 20 }}
      pos="fixed"
      h="full"
      zIndex={2}
      overflowY={isMenuOpen ? "scroll" : "-moz-hidden-unscrollable"}
      {...rest}
      css={{
        "&::-webkit-scrollbar": {
          display: "none",
        },
        "-ms-overflow-style": "none", // IE and Edge
        "scrollbar-width": "none", // Firefox
      }}
    >
      <Flex h="20" alignItems="center" mx="8" justifyContent="space-between">
        {isMenuOpen ? renderLogo() : null}
        <CloseButton display={{ base: "flex", md: "none" }} onClick={onClose} color="light.text" />
      </Flex>
      {MenuList.map((link) => (
        <NavItem
          key={link.name}
          icon={link.icon}
          subItems={link.subItems}
          name={link.name}
          fontSize="sm"
          h="2.5rem"
          path={link.path}
          onClick={() => (navigate(link.path), onClose())}
          fecharMobile={onClose}
        >
          {isMenuOpen ? link.name : undefined}
        </NavItem>
      ))}
    </Box>
  );
};
