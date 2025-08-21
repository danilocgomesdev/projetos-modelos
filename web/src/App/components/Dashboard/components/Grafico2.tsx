import { Box, Flex, GridItem, Text, VStack } from "@chakra-ui/react";
import { TrendingUp } from "lucide-react";
import { Area, AreaChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis } from "recharts";

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

export function Grafico2(): JSX.Element {
  return (
    <GridItem colSpan={{ base: 1, md: 6, lg: 4 }} m={1}>
      <Box borderWidth="1px" borderRadius="lg" p={4} boxShadow="md">
        <VStack align="start" spacing={2} mb={4}>
          <Text fontSize="lg" fontWeight="bold">
            Area Chart - Stacked
          </Text>
          <Text fontSize="sm" color="gray.500">
            Showing total visitors for the last 6 months
          </Text>
        </VStack>
        <Box w="full" h="150px">
          <ResponsiveContainer width="100%" height="100%">
            <AreaChart data={chartData} margin={{ left: 12, right: 12 }}>
              <CartesianGrid vertical={false} />
              <XAxis
                dataKey="month"
                tickLine={false}
                axisLine={false}
                tickMargin={8}
                tickFormatter={(value: string) => value.slice(0, 3)}
              />
              <Tooltip cursor={false} />
              <Area
                dataKey="mobile"
                type="natural"
                fill={chartConfig.mobile.color}
                fillOpacity={0.4}
                stroke={chartConfig.mobile.color}
                stackId="a"
              />
              <Area
                dataKey="desktop"
                type="natural"
                fill={chartConfig.desktop.color}
                fillOpacity={0.4}
                stroke={chartConfig.desktop.color}
                stackId="a"
              />
            </AreaChart>
          </ResponsiveContainer>
        </Box>
        <Flex direction="column" align="start" gap={2} fontSize="sm" mt={4}>
          <Flex align="center" gap={2} fontWeight="medium">
            Trending up by 5.2% this month <TrendingUp size={16} />
          </Flex>
          <Text color="gray.500">January - June 2024</Text>
        </Flex>
      </Box>
    </GridItem>
  );
}
