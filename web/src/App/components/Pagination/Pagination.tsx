import { Button, ButtonGroup, Flex, Text, useColorModeValue } from "@chakra-ui/react";
import { FiChevronLeft, FiChevronRight, FiChevronsLeft, FiChevronsRight } from "react-icons/fi";

interface PaginationPropsTypes {
  totalPages: number;
  totalItems: number;
  itemsPerPage: number;
  currentPage: number;
  setCurrentPage: (page: number) => void;
}

export const Pagination = ({
  totalPages,
  totalItems,
  itemsPerPage,
  currentPage,
  setCurrentPage,
}: PaginationPropsTypes) => {
  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = Math.min(startIndex + itemsPerPage, totalItems);
  const shouldShowPage = (pageNumber: number) => {
    if (totalPages <= 3) {
      return true;
    }

    if (currentPage <= 1) {
      return pageNumber <= 3;
    }

    if (currentPage >= totalPages) {
      return pageNumber >= totalPages - 2;
    }

    return Math.abs(pageNumber - currentPage) <= 1;
  };

  return (
    <Flex justifyContent="space-between" alignItems="center" w={"100%"} minWidth="fit-content">
      <Flex justify="flex" mt={5}>
        <ButtonGroup isAttached size={{ base: "xs", md: "sm" }}>
          <Button leftIcon={<FiChevronsLeft />} onClick={() => handlePageChange(1)} />
          <Button
            leftIcon={<FiChevronLeft />}
            isDisabled={currentPage === 1}
            onClick={() => handlePageChange(currentPage - 1)}
          />
          <Button hidden={currentPage <= 2} onClick={() => handlePageChange(currentPage - 2)}>
            ...
          </Button>
          {Array.from({ length: totalPages }).map((_, index) => {
            const pageNumber = index + 1;

            if (!shouldShowPage(pageNumber)) {
              return null;
            }
            return (
              <Button
                key={index}
                colorScheme={currentPage === index + 1 ? "blue" : undefined}
                onClick={() => handlePageChange(index + 1)}
              >
                {index + 1}
              </Button>
            );
          })}
          <Button
            hidden={currentPage > totalPages - 2}
            onClick={() => handlePageChange(currentPage + 2)}
          >
            ...
          </Button>
          <Button
            rightIcon={<FiChevronRight />}
            isDisabled={currentPage === totalPages}
            onClick={() => handlePageChange(currentPage + 1)}
          />
          <Button leftIcon={<FiChevronsRight />} onClick={() => handlePageChange(totalPages)} />
        </ButtonGroup>
      </Flex>
      <Text
        fontSize={{ base: "xs", md: "sm" }}
        alignSelf="end"
        color={useColorModeValue("light.colorDiscreta", "dark.colorDiscreta")}
      >
        Total Registros: {totalItems === 0 ? 0 : startIndex + 1}-{endIndex} / {totalItems}
      </Text>
    </Flex>
  );
};
