import { Box, Flex, GridItem, Text, useColorModeValue, VStack } from "@chakra-ui/react";
import { TrendingUp } from "lucide-react";
import {
  Bar,
  BarChart,
  CartesianGrid,
  LabelList,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";

const chartData = [
  { month: "January", desktop: 186, mobile: 80 },
  { month: "February", desktop: 305, mobile: 200 },
  { month: "March", desktop: 237, mobile: 120 },
  { month: "April", desktop: 73, mobile: 190 },
  { month: "May", desktop: 209, mobile: 130 },
  { month: "June", desktop: 214, mobile: 140 },
];

export function Grafico4(): JSX.Element {
  return (
    <GridItem colSpan={{ base: 1, md: 6, lg: 6 }} m={1}>
      <Box borderWidth="1px" borderRadius="lg" p={4} boxShadow="md">
        <VStack align="start" spacing={2} mb={4}>
          <Text fontSize="lg" fontWeight="bold">
            Bar Chart - Custom Label
          </Text>
          <Text fontSize="sm" color="gray.500">
            January - June 2024
          </Text>
        </VStack>
        <Box w="full" h="150px">
          <ResponsiveContainer width="100%" height="100%">
            <BarChart data={chartData} layout="vertical" margin={{ right: 16 }}>
              <CartesianGrid horizontal={false} />
              <YAxis
                dataKey="month"
                type="category"
                tickLine={false}
                tickMargin={10}
                axisLine={false}
                tickFormatter={(value) => value.slice(0, 3)}
                hide
              />
              <XAxis dataKey="desktop" type="number" hide />
              <Tooltip cursor={false} />
              <Bar dataKey="desktop" layout="vertical" fill="#4A90E2" radius={4}>
                <LabelList
                  dataKey="month"
                  position="insideLeft"
                  offset={8}
                  fill="#fff"
                  fontSize={12}
                />
                <LabelList
                  dataKey="desktop"
                  position="right"
                  offset={8}
                  fill={useColorModeValue("#000", "#ccc")}
                  fontSize={12}
                />
              </Bar>
            </BarChart>
          </ResponsiveContainer>
        </Box>
        <Flex direction="column" align="start" gap={2} fontSize="sm" mt={4}>
          <Flex align="center" gap={2} fontWeight="medium">
            Trending up by 5.2% this month <TrendingUp size={16} />
          </Flex>
          <Text color="gray.500">Showing total visitors for the last 6 months</Text>
        </Flex>
      </Box>
    </GridItem>
  );
}
