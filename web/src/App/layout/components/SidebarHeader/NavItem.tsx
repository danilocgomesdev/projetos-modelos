import {
  Accordion,
  AccordionButton,
  AccordionIcon,
  AccordionItem,
  AccordionPanel,
  Box,
  Divider,
  Flex,
  FlexProps,
  Icon,
  Link,
  Menu,
  MenuButton,
  MenuItem,
  MenuList,
  useColorMode,
  useDisclosure,
} from "@chakra-ui/react";
import { IconType } from "react-icons";
import { FiChevronDown } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { ValidacaoAcesso } from "../../../../utils/Acessos";
import useValidaPermissoes from "../../../hooks/useValidaPermissoes";
import { useSidebarStore } from "../../../stores/SidebarHeaderStore";

interface SubItemsProps extends FlexProps {
  name: string;
  divider?: boolean;
  path: string;
  validacaoAcesso?: ValidacaoAcesso;
}

interface NavItemProps extends FlexProps {
  icon: IconType;
  children?: string;
  name: string;
  subItems?: SubItemsProps[];
  path: string;
  validacaoAcesso?: ValidacaoAcesso;
  fecharMobile: () => void;
}

export const NavItem = ({
  icon,
  children,
  subItems,
  name,
  path,
  validacaoAcesso,
  fecharMobile,
  ...rest
}: NavItemProps) => {
  const navigate = useNavigate();
  const { isMenuOpen } = useSidebarStore();
  const { colorMode } = useColorMode();
  const { onClose } = useDisclosure();

  const { validaPermissoes } = useValidaPermissoes();

  if (validacaoAcesso && !validaPermissoes(validacaoAcesso)) {
    return <></>;
  }

  return (
    <>
      {subItems && subItems?.length > 0 ? (
        <Box transition="1s ease" zIndex={isMenuOpen ? "10" : "0"}>
          {isMenuOpen ? (
            <Accordion allowToggle>
              <AccordionItem borderBlock="none">
                <h2>
                  <AccordionButton>
                    <Box
                      as="span"
                      flex="1"
                      textAlign="left"
                      color="light.text"
                      fontSize="sm"
                      mx="4"
                    >
                      {icon && (
                        <Icon
                          mr="4"
                          fontSize="md"
                          title={name}
                          _groupHover={{
                            color: "white",
                          }}
                          as={icon}
                        />
                      )}
                      {children}
                    </Box>
                    <AccordionIcon color="light.text" mx="-3" />
                  </AccordionButton>
                </h2>

                <AccordionPanel pb={4}>
                  {subItems.map((x) => {
                    if (x.validacaoAcesso && !validaPermissoes(x.validacaoAcesso)) {
                      return <></>;
                    }

                    return (
                      <Box key={`${x.name}-expandido`}>
                        <Divider hidden={!x.divider} />
                        <Flex
                          align="center"
                          p="4"
                          pl="8"
                          color="light.text"
                          borderRadius="lg"
                          role="group"
                          cursor="pointer"
                          _hover={{
                            bg: "blue.400",
                            color: "white",
                          }}
                          {...rest}
                          onClick={() => (navigate(path + x.path), fecharMobile())}
                        >
                          {x.name}
                        </Flex>
                      </Box>
                    );
                  })}
                </AccordionPanel>
              </AccordionItem>
            </Accordion>
          ) : (
            <Menu>
              <Flex justifyContent="center" m={1}>
                <MenuButton title={name}>
                  <Flex flexDirection="row" justifyContent="space-around">
                    <Box
                      as="span"
                      flex="1"
                      textAlign="left"
                      color="light.text"
                      fontSize="sm"
                      ml={5}
                      mb={1}
                    >
                      {icon && (
                        <Icon
                          mt={1}
                          fontSize="x-large"
                          title={name}
                          _groupHover={{
                            color: "white",
                          }}
                          as={icon}
                        />
                      )}
                      {children}
                    </Box>
                    <Box justifyContent="center" mt={2} ml={2}>
                      <FiChevronDown color={colorMode === "light" ? "white" : "dark.primary"} />
                    </Box>
                  </Flex>
                </MenuButton>
              </Flex>
              <MenuList
                color="light.text"
                borderColor={colorMode === "light" ? "light.border" : "dark.border"}
                bg={colorMode === "light" ? "light.primary" : "dark.primary"}
                ml={6}
              >
                {subItems.map((x) => {
                  if (x.validacaoAcesso && !validaPermissoes(x.validacaoAcesso)) {
                    return <></>;
                  }

                  return (
                    <Box key={x.name}>
                      <Divider hidden={!x.divider} />
                      <MenuItem
                        onClick={() => (navigate(path + x.path), onClose())}
                        fontSize="sm"
                        bg={colorMode === "light" ? "light.primary" : "dark.primary"}
                        _hover={{
                          bg: "hoverColor",
                          color: "white",
                        }}
                      >
                        {x.name}
                      </MenuItem>
                    </Box>
                  );
                })}
              </MenuList>
            </Menu>
          )}
        </Box>
      ) : (
        <Link href="#" style={{ textDecoration: "none" }} _focus={{ boxShadow: "none" }}>
          <Flex
            align="center"
            p="4"
            mx={isMenuOpen ? "4" : "1"}
            borderRadius="lg"
            role="group"
            cursor="pointer"
            color="light.text"
            title={name}
            _hover={{
              bg: "hoverColor",
              color: "white",
            }}
            width="90%"
            justifyContent={isMenuOpen ? "flex-start" : "center"}
            {...rest}
          >
            {icon && (
              <Icon
                mr="1.5"
                fontSize={isMenuOpen ? "md" : "x-large"}
                _groupHover={{
                  color: "white",
                }}
                as={icon}
              />
            )}
            {children}
          </Flex>
        </Link>
      )}
    </>
  );
};
