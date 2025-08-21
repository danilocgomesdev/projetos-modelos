import {
  Box,
  Divider,
  Flex,
  FlexProps,
  HStack,
  IconButton,
  Image,
  Link,
  Menu,
  MenuButton,
  MenuDivider,
  MenuItem,
  MenuList,
  Text,
  VStack,
  useBreakpointValue,
  useColorMode,
  useColorModeValue,
  useDisclosure,
  useMediaQuery,
} from "@chakra-ui/react";
import {
  FiChevronDown,
  FiChevronLeft,
  FiChevronRight,
  FiMenu,
  FiMoon,
  FiSun,
  FiUserCheck,
} from "react-icons/fi";
import logoFieg from "../../../../assets/fieg.svg";
import logoIel from "../../../../assets/iel.svg";
import logoSenai from "../../../../assets/senai.svg";
import logoSesi from "../../../../assets/sesi.svg";
import { invalidaCachePermissoes } from "../../../../requests/invalidaCachePermissoes";
import NotificacoesIcon from "../../../components/NotificacoesIcon/NotificacoesIcon";
import useCR5Axios from "../../../hooks/useCR5Axios";
import { useKeycloak } from "../../../hooks/useKeyCloak";
import { usePrimaryColor } from "../../../hooks/usePrimaryColor";
import { useOperadorEDireitosStore } from "../../../stores/OperadorEDireitosStore";
import { useSidebarStore } from "../../../stores/SidebarHeaderStore";
import { useUnidadeStore } from "../../../stores/UnidadeStore";
import { ModalUnidade } from "./ModalUnidade";

interface MobileProps extends FlexProps {
  onOpens: () => void;
}

export const MobileNav = ({ onOpens, ...rest }: MobileProps) => {
  const unidadeAtual = useUnidadeStore((s) => s.unidadeAtual);
  const { axios } = useCR5Axios();
  const { colorMode, toggleColorMode } = useColorMode();
  const logoSize = useBreakpointValue({ base: "0rem", md: "12rem" });
  const { isMenuOpen, setMenuOpen } = useSidebarStore();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const { keycloak } = useKeycloak();
  const operador = useOperadorEDireitosStore((state) => state.operador);
  const [isMobile] = useMediaQuery("(max-width: 780px)");

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
    <Flex
      transition="1s ease"
      ml={{ base: 0, md: isMenuOpen ? 60 : 20 }}
      px={{ base: 4, md: 4 }}
      h={{ base: "auto", lg: 20 }}
      alignItems="center"
      bg={usePrimaryColor()}
      borderTop={0}
      borderBottomWidth="1px"
      borderBottomColor={useColorModeValue("light.border", "dark.border")}
      justifyContent={{ base: "space-between", md: "flex" }}
      {...rest}
    >
      {isMenuOpen ? null : renderLogo()}
      <IconButton
        display={{ base: "flex", md: "none" }}
        onClick={onOpens}
        variant="outline"
        aria-label="open menu"
        color="light.text"
        _hover={{
          bg: "hoverColor",
          color: "white",
        }}
        icon={<FiMenu />}
      />
      <Box
        ml={"-30px"}
        mt={1}
        height="27px"
        position="absolute"
        transition="1s ease"
        border="1px solid"
        borderColor={useColorModeValue("light.border", "dark.border")}
        borderRadius={50}
        bg={usePrimaryColor()}
        cursor="pointer"
        zIndex={3}
      >
        {!isMobile &&
          (isOpen ? null : isMenuOpen ? (
            <FiChevronLeft
              onClick={() => setMenuOpen(!isMenuOpen)}
              fontSize="x-large"
              color="white"
            />
          ) : (
            <FiChevronRight
              onClick={() => setMenuOpen(!isMenuOpen)}
              fontSize="x-large"
              color="white"
            />
          ))}
      </Box>
      <Flex direction="row" wrap="wrap" w={"100%"}>
        <Flex m={5} direction="column" minW={"150px"} flex={1}>
          <Text fontSize={"lg"} fontWeight="bold" color="light.text">
            CR5-V2
          </Text>
          <Text fontSize="xs" color="light.text">
            Versão: {process.env.APP_VERSION}
          </Text>
        </Flex>
        <Flex justifyContent="flex-end" minW={"200px"} wrap="wrap" flex={1}>
          <Box
            onClick={onOpen}
            color="light.text"
            cursor="pointer"
            display="flex"
            fontSize={isMobile ? "xs" : "sm"}
            flexDirection="column"
            alignItems="flex-end"
            p="1rem"
            _hover={{
              bg: "hoverColor",
              color: "white",
              borderRadius: "5px",
            }}
          >
            <Flex direction={"column"}>
              <Box>
                UNIDADE: {unidadeAtual.codigo} - {unidadeAtual.nome}
              </Box>
              <Box> FILIAL: {unidadeAtual.filialERP}</Box>
            </Flex>
          </Box>
        </Flex>
      </Flex>
      <Divider orientation="vertical" minH={20} />
      <HStack spacing={{ base: "0", md: "6" }} ml="auto">
        <IconButton
          size="lg"
          variant="ghost"
          onClick={toggleColorMode}
          aria-label="open menu"
          _hover={{
            bg: "hoverColor",
            color: "white",
          }}
          color="light.text"
          icon={colorMode === "light" ? <FiMoon /> : <FiSun />}
        />
        <NotificacoesIcon />
        <Flex alignItems="center">
          <Menu>
            <MenuButton py={2} transition="all 0.3s" _focus={{ boxShadow: "none" }}>
              <HStack color="light.text">
                <FiUserCheck />
                <VStack
                  display={{ base: "flex", md: "flex" }}
                  alignItems="flex-start"
                  spacing="1px"
                  ml="2"
                >
                  <Text
                    fontSize={"sm"}
                    display={{ base: "none", md: "block" }}
                    color="light.text"
                    textAlign={"start"}
                  >
                    {operador?.nome}
                  </Text>
                </VStack>
                <Box display={{ base: "none", md: "flex" }}>
                  <FiChevronDown />
                </Box>
              </HStack>
            </MenuButton>
            <MenuList
              color="light.text"
              bg={useColorModeValue("light.primary", "dark.primary")}
              borderColor={useColorModeValue("light.border", "dark.border")}
            >
              <MenuItem
                display={{ base: "block", md: "none" }}
                bg={useColorModeValue("light.primary", "dark.primary")}
                fontSize="sm"
                cursor={"default"}
              >
                {operador?.nome}
              </MenuItem>
              <MenuDivider display={{ base: "block", md: "none" }} />
              <Link
                href={`https://oidc.fieg.com.br/auth/realms/${keycloak.realm}/account`}
                style={{ textDecoration: "none" }}
                _focus={{ boxShadow: "none" }}
              >
                <MenuItem
                  bg={useColorModeValue("light.primary", "dark.primary")}
                  fontSize="sm"
                  _hover={{
                    bg: "hoverColor",
                    color: "white",
                  }}
                >
                  Informações
                </MenuItem>
              </Link>
              <Link
                href={`https://oidc.fieg.com.br/auth/realms/${keycloak.realm}/account/applications`}
                style={{ textDecoration: "none" }}
                _focus={{ boxShadow: "none" }}
              >
                <MenuItem
                  bg={useColorModeValue("light.primary", "dark.primary")}
                  fontSize="sm"
                  _hover={{
                    bg: "hoverColor",
                    color: "white",
                  }}
                >
                  Histórico de acesso
                </MenuItem>
              </Link>
              <MenuDivider />
              <Link
                href="https://oidc.fieg.com.br/trocasenha"
                style={{ textDecoration: "none" }}
                _focus={{ boxShadow: "none" }}
              >
                <MenuItem
                  bg={useColorModeValue("light.primary", "dark.primary")}
                  fontSize="sm"
                  _hover={{
                    bg: "hoverColor",
                    color: "white",
                  }}
                >
                  Troca Senha
                </MenuItem>
              </Link>

              <MenuItem
                bg={useColorModeValue("light.primary", "dark.primary")}
                fontSize="sm"
                cursor="pointer"
                onClick={() => {
                  invalidaCachePermissoes(axios, 3000).finally(keycloak.logout);
                }}
                _hover={{
                  bg: "hoverColor",
                  color: "white",
                }}
              >
                Sair
              </MenuItem>
            </MenuList>
          </Menu>
        </Flex>
      </HStack>
      <ModalUnidade isOpen={isOpen} onClose={onClose} />
    </Flex>
  );
};
