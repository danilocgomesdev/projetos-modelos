import {
  Badge,
  Box,
  Button,
  IconButton,
  Menu,
  MenuButton,
  MenuDivider,
  MenuItem,
  MenuList,
  useColorModeValue,
} from "@chakra-ui/react";
import { FiBell, FiTrash2 } from "react-icons/fi";
import { useNotificacoes } from "./useNotificacoes";

const NotificacoesIcon = () => {
  const { notificacoes, marcarComoLida, excluirNotificacao } = useNotificacoes();
  const unreadNotificationsCount = notificacoes.filter((n) => !n.lida).length;
  const bg = useColorModeValue("light.primary", "dark.primary");

  return (
    <Menu>
      <MenuButton
        as={Box}
        position="relative"
        cursor={"pointer"}
        borderRadius="md"
        ml={-4}
        _hover={{
          bg: "hoverColor",
          color: "white",
        }}
      >
        <IconButton
          aria-label="Notificações"
          icon={<FiBell />}
          variant="ghost"
          size="lg"
          color="light.text"
        />

        {unreadNotificationsCount > 0 && (
          <Badge
            colorScheme="red"
            borderRadius="full"
            position="absolute"
            top="0"
            right="0"
            fontSize="0.8em"
            px={2}
          >
            {unreadNotificationsCount}
          </Badge>
        )}
      </MenuButton>

      <MenuList
        color="light.text"
        bg={bg}
        maxHeight="60vh"
        overflowY="auto"
        css={{
          scrollbarWidth: "none", // Para Firefox
          msOverflowStyle: "none", // Para IE/Edge
        }}
      >
        {notificacoes.length === 0 ? (
          <MenuItem
            bg={bg}
            _hover={{
              bg: "hoverColor",
              color: "white",
            }}
          >
            Nenhuma nova notificação
          </MenuItem>
        ) : (
          notificacoes.reverse().map((notificacao, index, array) => (
            <Box key={index}>
              <MenuItem
                onClick={() => marcarComoLida(index)}
                bg={bg}
                _hover={{
                  bg: "hoverColor",
                  color: "white",
                }}
                display="flex"
                alignItems="center"
                style={{ wordBreak: "break-word" }}
              >
                <Box display="flex" alignItems="center" justifyContent="space-between" width="100%">
                  {!notificacao.lida && (
                    <Badge colorScheme="red" borderRadius="full" size="lg" mr={4} p={1} />
                  )}
                  <Box maxWidth="200px" overflow="hidden" textOverflow="ellipsis">
                    {notificacao.mensagem}
                  </Box>
                </Box>
              </MenuItem>

              <Box display="flex" justifyContent="flex-end" p={2}>
                <Button
                  aria-label="Excluir Notificação"
                  leftIcon={<FiTrash2 />}
                  variant="ghost"
                  color={"text.light"}
                  _hover={{
                    bg: "hoverColor",
                    color: "white",
                  }}
                  size="xs"
                  onClick={(e) => {
                    e.stopPropagation();
                    excluirNotificacao(index);
                  }}
                >
                  Excluir
                </Button>
              </Box>

              {index < array.length - 1 && <MenuDivider />}
            </Box>
          ))
        )}
      </MenuList>
    </Menu>
  );
};

export default NotificacoesIcon;
