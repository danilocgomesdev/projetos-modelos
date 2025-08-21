import { Flex, Grid, Text } from "@chakra-ui/react";
import { FiChevronRight } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import useBreadcrumbs from "use-react-router-breadcrumbs";
import { EnvironmentAlert } from "../../../../utils/EnvironmentAlert";
import { routes } from "../../../Routes/Router";
import { MenuList } from "../Menus";

export function NavigationHeader() {
  const breadcrumbs = useBreadcrumbs();
  const navigate = useNavigate();

  const handleNavigator = (rota: string) => {
    const hasSubItems = MenuList.some((item) => item.subItems && item.path === rota);
    navigate(hasSubItems ? "/" : rota);
  };

  const buscarNomeRota = (rota: string) => {
    return routes.find((a) => a.path === rota) || null;
  };

  return (
    <Grid
      templateColumns={{ base: "1fr", md: "3fr 1fr" }}
      gap={4}
      px="1rem"
      py={2}
      alignItems="start"
    >
      {/* Breadcrumbs */}
      <Flex direction="column" overflowX="auto">
        <Flex wrap="nowrap" align="center">
          <Text fontWeight="semibold" fontSize={{ base: "xs", md: "sm" }} mr="2">
            Cr5-web-v2
          </Text>

          {breadcrumbs.map((breadcrumb, index) => {
            const rota = buscarNomeRota(breadcrumb.match.pathname);
            const isLast = index === breadcrumbs.length - 1;

            if (!rota) return null;

            return (
              <Flex key={breadcrumb.key} align="center">
                <Text
                  fontSize={{ base: "xs", md: "sm" }}
                  onClick={() => handleNavigator(rota.path)}
                  cursor="pointer"
                  mr="2"
                  whiteSpace="nowrap"
                >
                  {rota.id}
                </Text>
                {!isLast && <FiChevronRight color="gray.500" />}
              </Flex>
            );
          })}
        </Flex>
      </Flex>
      <EnvironmentAlert />
    </Grid>
  );
}
