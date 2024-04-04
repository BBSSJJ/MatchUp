"use client"
import React, { useCallback, useMemo, useState } from "react";
import {
  Table,
  TableHeader,
  TableColumn,
  TableBody,
  TableRow,
  TableCell,
  Input,
  Button,
  DropdownTrigger,
  Dropdown,
  DropdownMenu,
  DropdownItem,
  Chip,
  User,
  Pagination,
} from "@nextui-org/react";
import {PlusIcon} from "./PlusIcon";
import {VerticalDotsIcon} from "./VerticalDotsIcon";
import { SearchIcon } from "../SearchIcon";
import {ChevronDownIcon} from "./ChevronDownIcon";
import {columns, users, statusOptions, columns2} from "./dummyData";
import {capitalize} from "@/utils/utils";
import { useRouter } from "next/navigation";
import useSWR from 'swr';
import { SERVER_API_URL } from "@/utils/instance-axios";
import styles from './articleList.module.css'
import './article.css'
import { isLoggedInAtom } from "@/store/authAtom";
import { useAtom } from "jotai";

const fetcher = async (url) => {
  const response = await fetch(url); // 서버로부터 데이터 가져오기
  if (!response.ok) {
    throw new Error('Failed to fetch data');
  }
  return response.json(); // JSON 형식으로 변환하여 반환
};


const INITIAL_VISIBLE_COLUMNS = ["id", "title", "author", "views", "createdAt"];
export default function ArticleList() {
  const [isLoggedIn, setIsLoggedIn] = useAtom(isLoggedInAtom)
  const router = useRouter();
  const [filterValue, setFilterValue] = React.useState("");
  const [visibleColumns, setVisibleColumns] = useState(new Set(INITIAL_VISIBLE_COLUMNS));
  // const [statusFilter, setStatusFilter] = React.useState("all");
  const [rowsPerPage, setRowsPerPage] = React.useState(10);
  const [sortDescriptor, setSortDescriptor] = useState({
    column: "createdAt",
    direction: "descending",
  });
  const [page, setPage] = React.useState(1);
 
  // 데이터 가져오기
  const { data: articles, error, isLoading } = useSWR(
    `${SERVER_API_URL}/api/mz/articles`, 
    fetcher,
    {
      onErrorRetry: (error, key, config, revalidate, { retryCount }) => {
        if (error.status === 401) return
      }, 
      revalidateOnFocus: false,
      revalidateOnMount: true,
    },
    { refreshInterval: 500 }
  )

  // const pages = Math.ceil(articles?.list?.length / rowsPerPage);

  const hasSearchFilter = Boolean(filterValue);

  const headerColumns = React.useMemo(() => {
    if (typeof visibleColumns === "string" && visibleColumns === "all") {
      return columns2;
    }; 

    return columns2.filter((column2) => Array.from(visibleColumns).includes(column2.uid));
  }, [visibleColumns]);

  const filteredItems = React.useMemo(() => {
    let filteredArticles = [...(articles?.list || [])];

    if (hasSearchFilter) {
      filteredArticles = filteredArticles.filter((article) =>
        article.title.toLowerCase().includes(filterValue.toLowerCase()),
      );
    }
    // if (statusFilter !== "all" && Array.from(statusFilter).length !== statusOptions.length) {
    //   filteredArticles = filteredArticles.filter((user) =>
    //     Array.from(statusFilter).includes(user.status),
    //   );
    // }

    return filteredArticles.reverse();
  }, [articles, filterValue]);

  const pages = React.useMemo(() => {
    if (hasSearchFilter) {
      return Math.ceil(filteredItems.length / rowsPerPage);
    } else {
      return Math.ceil((articles?.list?.length || 0) / rowsPerPage);
    }
  }, [articles?.list, filteredItems, hasSearchFilter, rowsPerPage]);

  const items = React.useMemo(() => {
    const start = (page - 1) * rowsPerPage;
    const end = start + rowsPerPage;

    return filteredItems.slice(start, end);
  }, [page, filteredItems, rowsPerPage]);

  const sortedItems = React.useMemo(() => {
    return [...items].sort((a, b) => {
      const first = sortDescriptor.column === 'views' ? parseInt(a[sortDescriptor.column], 10) : a[sortDescriptor.column];
      const second =sortDescriptor.column === 'views' ? parseInt(b[sortDescriptor.column], 10) : b[sortDescriptor.column];
      const cmp = first < second ? -1 : first > second ? 1 : 0;

      return sortDescriptor.direction === "descending" ? -cmp : cmp;
    });
  }, [sortDescriptor, items]);

  const renderCell = React.useCallback((article, columnKey) => {
    const cellValue = article[columnKey];

    switch (columnKey) {
      case "title": // 제목 칼럼에서 보여줄 내용
        return (
          <div className="flex flex-col h-[45px]">
            <p className="text-bold text-small capitalize">{cellValue}</p>
            {/* 투표현황 */}
            <div className={styles.articleVote}>
              {/* width를 실제 데이터와 연동 */}
              <div className="articleVoteLeftTiny text-tiny" style={{ width: `${article.leftSympathyCount === article.rightSympathyCount ? '50%' : `${(article.leftSympathyCount / (article.leftSympathyCount + article.rightSympathyCount)) * 100}%`}`, height: "15px" }}><span>{article.leftSympathyCount}</span></div>
              <div className="articleVoteRightTiny text-tiny" style={{ width: `${article.leftSympathyCount === article.rightSympathyCount ? '50%' : `${(article.rightSympathyCount / (article.leftSympathyCount + article.rightSympathyCount)) * 100}%`}`, height: "15px" }}><span>{article.rightSympathyCount}</span></div>

              {/* <div className="articleVoteLeftTiny text-tiny" style={{ width: `${article.leftSympathyCount}%`, height: "15px" }}><span>{article.leftSympathyCount}</span></div>
              <div className="articleVoteRightTiny text-tiny" style={{ width: `${article.rightSympathyCount}%`, height: "15px" }}><span>{article.rightSympathyCount}</span></div> */}
            </div>
          </div>
        );
      case "role":
        return (
          <div className="flex flex-col">
            <p className="text-bold text-small capitalize">{cellValue}</p>
          </div>
        );
      // case "status":
      //   return (
      //     <Chip
      //       className="capitalize border-none gap-1 text-default-600"
      //       color={statusColorMap[user.status]}
      //       size="sm"
      //       variant="dot"
      //     >
      //       {cellValue}
      //     </Chip>
      //   );
      case "actions":
        return (
          <div className="relative flex justify-end items-center gap-2">
            <Dropdown className="bg-background border-1 border-default-200">
              <DropdownTrigger>
                <Button isIconOnly radius="full" size="sm" variant="light">
                  <VerticalDotsIcon className="text-default-400" />
                </Button>
              </DropdownTrigger>
              <DropdownMenu>
                <DropdownItem>View</DropdownItem>
                <DropdownItem>Edit</DropdownItem>
                <DropdownItem>Delete</DropdownItem>
              </DropdownMenu>
            </Dropdown>
          </div>
        );
      default: // 조건에 해당하지 않는 경우 원래값 그대로 보여줌
        return cellValue;
    }
  }, []);

  const onRowsPerPageChange = React.useCallback((e) => {
    setRowsPerPage(Number(e.target.value));
    setPage(1);
  }, []);


  const onSearchChange = React.useCallback((value) => {
    if (value) {
      setFilterValue(value);
      setPage(1);
    } else {
      setFilterValue("");
    }
  }, []);

  const topContent = React.useMemo(() => {
    
    const handleCreateArticle = () => {
      router.push('/article/create')
    }

    return (
      <div className="flex flex-col gap-4">
        <div className="flex justify-between gap-3 items-end">
          <Input
            isClearable
            classNames={{
              base: "w-full sm:max-w-[44%]",
              inputWrapper: "border-1",
            }}
            placeholder="Search by title..."
            size="sm"
            startContent={<SearchIcon className="text-default-300" />}
            value={filterValue}
            variant="bordered"
            onClear={() => setFilterValue("")}
            onValueChange={onSearchChange}
          />
          <div className="flex gap-3">
            {/* <Dropdown>
              <DropdownTrigger className="hidden sm:flex">
                <Button
                  endContent={<ChevronDownIcon className="text-small" />}
                  size="sm"
                  variant="flat"
                >
                  Status
                </Button>
              </DropdownTrigger>
              <DropdownMenu
                disallowEmptySelection
                aria-label="Table Columns"
                closeOnSelect={false}
                selectionMode="multiple"

              >
                {statusOptions.map((status) => (
                  <DropdownItem key={status.uid} className="capitalize">
                    {capitalize(status.name)}
                  </DropdownItem>
                ))}
              </DropdownMenu>
            </Dropdown> */}
            <Dropdown>
              <DropdownTrigger className="hidden sm:flex">
                <Button
                  endContent={<ChevronDownIcon className="text-small" />}
                  size="sm"
                  variant="flat"
                >
                  Columns
                </Button>
              </DropdownTrigger>
              <DropdownMenu
                disallowEmptySelection
                aria-label="Table Columns"
                closeOnSelect={false}
                selectedKeys={visibleColumns}
                selectionMode="multiple"
                onSelectionChange={setVisibleColumns}
              >
                {columns2.map((column) => (
                  <DropdownItem key={column.uid} className="capitalize">
                    {capitalize(column.name)}
                  </DropdownItem>
                ))}
              </DropdownMenu>
            </Dropdown>
            <Button
              className="bg-foreground text-background"
              endContent={<PlusIcon />}
              onClick={() => {isLoggedIn ? handleCreateArticle : window.alert("로그인이 필요합니다.")}}
              size="sm"
            >
              Add New
            </Button>
          </div>
        </div>
        <div className="flex justify-between items-center">
          <span className="text-default-400 text-small">Total {articles?.list?.length} articles</span>
        </div>
      </div>
    );
  }, [
    filterValue,
    visibleColumns,
    onSearchChange,
    articles?.length,
    hasSearchFilter,
  ]);

  // pagination
  const bottomContent = React.useMemo(() => {
    return (
      <div className="py-2 px-2 flex justify-between items-center">
        <Pagination
          showControls
          classNames={{
            cursor: "bg-foreground text-background",
          }}
          color="secondary"
          showShadow
          // isDisabled={hasSearchFilter}
          page={page}
          total={pages}
          variant="light"
          onChange={setPage}
        />
        {/* <span className="text-small text-default-400">
          {selectedKeys === "all"
            ? "All items selected"
            : `${selectedKeys.size} of ${items.length} selected`}
        </span> */}
      </div>
    );
  }, [ items.length, page, pages, hasSearchFilter]);

  const classNames = React.useMemo(
    () => ({
      wrapper: ["max-h-[382px]", "max-w-3xl", "mx-3"],
      th: ["bg-[#408787]", "text-default-700", "border-b", "border-divider", "text-[20px]"],
      td: [
        // changing the rows border radius
        // first
        "group-data-[first=true]:first:before:rounded-none",
        "group-data-[first=true]:last:before:rounded-none",
        // middle
        "group-data-[middle=true]:before:rounded-none",
        // last
        "group-data-[last=true]:first:before:rounded-none",
        "group-data-[last=true]:last:before:rounded-none",
      ],
    }),
    [],
  );

  const handleRowClick = (id) => {
    router.push(`/article/${id}`)
  }

  if (error) return <div>Failed to load</div>
  if (isLoading) return <div>Loading...</div>;

  return (
    <div className="m-[5%]">
      <Table
        isCompact
        removeWrapper
        aria-label="Example table with custom cells, pagination and sorting"
        bottomContent={bottomContent}
        bottomContentPlacement="outside"
        // checkboxesProps={{
        //   classNames: {
        //     wrapper: "after:bg-foreground after:text-background text-background",
        //   },
        // }}
        classNames={classNames}
        sortDescriptor={sortDescriptor}
        topContent={topContent}
        topContentPlacement="outside"
        onSortChange={setSortDescriptor}
      >
        <TableHeader columns={headerColumns}>
          {(column) => (
            <TableColumn
              key={column.uid}
              align={column.uid === "title" ? "center" : "start"}
              allowsSorting={column.sortable}
              width={column.uid === "id" ? "30px" : column.uid === "title" ? "200px" : "100px"}
            >
              {column.name}
            </TableColumn>
          )}
        </TableHeader>
        <TableBody emptyContent={"No articles found"} items={sortedItems}>
          {(item) => (
            // 테이블의 각 행
            <TableRow key={item.id} onClick={()=>handleRowClick(item.id)}>
              {(columnKey) => <TableCell>{renderCell(item, columnKey)}</TableCell>}
            </TableRow>
          )}
        </TableBody>
      </Table>
    </div>
  );
}

