import { defineStore } from 'pinia';

export const useCurrentPageInMain = defineStore('currentPageInMain', {
    state: () => ({
        currentPage: "CoursePage",
    }),
    actions: {
        setCurrentPage(page) {
            this.currentPage = page;
            console.log("Switch to ", this.currentPage);
        }
    },
});
