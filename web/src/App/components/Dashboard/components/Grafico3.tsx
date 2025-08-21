import { Box, Flex, GridItem, Text, useColorModeValue, VStack } from "@chakra-ui/react";
import { TrendingUp } from "lucide-react";
import {
  Label,
  PolarRadiusAxis,
  RadialBar,
  RadialBarChart,
  ResponsiveContainer,
  Tooltip,
} from "recharts";

const chartData = [{ month: "January", desktop: 1260, mobile: 570 }];

export function Grafico3(): JSX.Element {
  const totalVisitors = chartData[0].desktop + chartData[0].mobile;

  return (
    <GridItem colSpan={{ base: 1, md: 6, lg: 4 }} m={1}>
      <Box borderWidth="1px" borderRadius="lg" p={4} boxShadow="md">
        <VStack align="center" spacing={2} mb={4}>
          <Text fontSize="lg" fontWeight="bold">
            Radial Chart - Stacked
          </Text>
          <Text fontSize="sm" color="gray.500">
            January - June 2024
          </Text>
        </VStack>
        <Box w="full" h="150px" p={2} display="flex" justifyContent="center" alignItems="center">
          <ResponsiveContainer width={"5rem"} height={250}>
            <RadialBarChart data={chartData} endAngle={180} innerRadius={80} outerRadius={130}>
              <Tooltip cursor={false} />
              <PolarRadiusAxis tick={false} tickLine={false} axisLine={false}>
                <Label
                  content={({ viewBox }) => {
                    if (viewBox && "cx" in viewBox && "cy" in viewBox) {
                      return (
                        <text x={viewBox.cx} y={viewBox.cy} textAnchor="middle">
                          <tspan
                            x={viewBox.cx}
                            y={(viewBox.cy || 0) - 14}
                            fill={useColorModeValue("#000", "#ccc")}
                            fontSize="20"
                            fontWeight="bold"
                          >
                            {totalVisitors.toLocaleString()}
                          </tspan>
                          <tspan x={viewBox.cx} y={(viewBox.cy || 0) + 4} fill="#888" fontSize="14">
                            Visitors
                          </tspan>
                        </text>
                      );
                    }
                  }}
                />
              </PolarRadiusAxis>
              <RadialBar
                dataKey="desktop"
                stackId="a"
                cornerRadius={5}
                fill="#4A90E2"
                stroke="transparent"
                strokeWidth={2}
              />
              <RadialBar
                dataKey="mobile"
                fill="#50C878"
                stackId="a"
                cornerRadius={5}
                stroke="transparent"
                strokeWidth={2}
              />
            </RadialBarChart>
          </ResponsiveContainer>
        </Box>
        <Flex direction="column" align="center" gap={2} fontSize="sm" mt={4}>
          <Flex align="center" gap={2} fontWeight="medium">
            Trending up by 5.2% this month <TrendingUp size={16} />
          </Flex>
          <Text color="gray.500">Showing total visitors for the last 6 months</Text>
        </Flex>
      </Box>
    </GridItem>
  );
}
