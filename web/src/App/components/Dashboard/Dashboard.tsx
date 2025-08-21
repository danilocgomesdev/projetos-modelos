import { Grid } from "@chakra-ui/react";
import { Grafico1 } from "./components/Grafico1";
import { Grafico2 } from "./components/Grafico2";
import { Grafico3 } from "./components/Grafico3";
import { Grafico4 } from "./components/Grafico4";
import { Grafico5 } from "./components/Grafico5";

export function Dashboard(): JSX.Element {
  return (
    <>
      <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} mt={4} p={1}>
        <Grafico1 />
        <Grafico2 />
        <Grafico3 />
        <Grafico4 />
        <Grafico5 />
      </Grid>
    </>
  );
}
