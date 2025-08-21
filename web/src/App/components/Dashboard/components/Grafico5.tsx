import { Box, Flex, GridItem, Text, VStack } from "@chakra-ui/react";
import { TrendingUp } from "lucide-react";
import { Bar, BarChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis } from "recharts";

type ChartData = {
  month: string;
  desktop: number;
  mobile: number;
};

const chartData: ChartData[] = [
  { month: "January", desktop: 186, mobile: 80 },
  { month: "February", desktop: 305, mobile: 200 },
  { month: "March", desktop: 237, mobile: 120 },
  { month: "April", desktop: 73, mobile: 190 },
  { month: "May", desktop: 209, mobile: 130 },
  { month: "June", desktop: 214, mobile: 140 },
];

type ChartConfig = {
  desktop: {
    label: string;
    color: string;
  };
  mobile: {
    label: string;
    color: string;
  };
};

const chartConfig: ChartConfig = {
  desktop: {
    label: "Desktop",
    color: "#4A90E2",
  },
  mobile: {
    label: "Mobile",
    color: "#50E3C2",
  },
};

export function Grafico5(): JSX.Element {
  return (
    <GridItem colSpan={{ base: 1, md: 6, lg: 6 }} m={1}>
      <Box borderWidth="1px" borderRadius="lg" p={4} boxShadow="md">
        <VStack align="start" spacing={2} mb={4}>
          <Text fontSize="lg" fontWeight="bold">
            Bar Chart - Multiple
          </Text>
          <Text fontSize="sm" color="gray.500">
            January - June 2024
          </Text>
        </VStack>
        <Box w="full" h="150px">
          <ResponsiveContainer width="100%" height="100%">
            <BarChart data={chartData}>
              <CartesianGrid vertical={false} />
              <XAxis
                dataKey="month"
                tickLine={false}
                tickMargin={10}
                axisLine={false}
                tickFormatter={(value: string) => value.slice(0, 3)}
              />
              <Tooltip cursor={false} />
              <Bar dataKey="desktop" fill={chartConfig.desktop.color} radius={4} />
              <Bar dataKey="mobile" fill={chartConfig.mobile.color} radius={4} />
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
