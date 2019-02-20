import {Item, ItemList, ItemQuery, ItemUpdate, emptyItemQuery} from './item.js';

export default class Store {
    /**
     * @param {!string} name Database name
     * @param {function()} [callback] Called when the Store is ready
     */
    constructor(name, callback) {
        /**
         * @type {Storage}
         */
        const localStorage = window.localStorage;

        /**
         * @type {ItemList}
         */
        let liveTodos;

        this.getReq = () => {
            // if (liveTodos)
            //     return liveTodos;
            let req = new XMLHttpRequest();
            req.open('GET', "/todo/load", true);
            req.send(null);
            return req;
        };

        /**
         * Write the local ItemList to localStorage.
         *
         * @param {ItemList} todos Array of todos to write
         */
        this.setLocalStorage = (todos) => {
            console.warn("setLocalStorage");
            let xhr = new XMLHttpRequest();
            xhr.open('POST', '/todo/save', true);
            xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
            console.log(">>> " + JSON.stringify(todos));
            xhr.send(JSON.stringify(todos));
            xhr.onloadend = function () {
                if (callback) {
                    callback();
                }
                console.log("save done")
            };
        };

    }

    /**
     * Find items with properties matching those on query.
     *
     * @param {ItemQuery} query Query to match
     * @param {function(ItemList)} callback Called when the query is done
     *
     * @example
     * db.find({completed: true}, data => {
     *	 // data shall contain items whose completed properties are true
     * })
     */
    find(query, callback) {
        let req = this.getReq();
        req.onloadend = () => {
                const todos = JSON.parse(req.responseText);
                console.log("<<< (find)" + JSON.stringify(todos));
                let k;
                callback(todos.filter(todo => {
                    for (k in query) {
                        if (query[k] !== todo[k]) {
                            return false;
                        }
                    }
                    return true;
                }));
            }
        };
    }

    /**
     * Update an item in the Store.
     *
     * @param {ItemUpdate} update Record with an id and a property to update
     * @param {function()} [callback] Called when partialRecord is applied
     */
    update(update, callback) {
        let req = this.getReq();
        req.onloadend = () => {
            const id = update.id;
            const todos = JSON.parse(req.responseText);
            console.log("<<< (update)" + JSON.stringify(todos));
            let i = todos.length;
            let k;

            while (i--) {
                if (todos[i].id === id) {
                    for (k in update) {
                        todos[i][k] = update[k];
                    }
                    break;
                }
            }

            this.setLocalStorage(todos);

            if (callback) {
                callback();
            }
        }
    }

    /**
     * Insert an item into the Store.
     *
     * @param {Item} item Item to insert
     * @param {function()} [callback] Called when item is inserted
     */
    insert(item, callback) {
        let req = this.getReq();
        req.onloadend = () => {
            const todos = JSON.parse(req.responseText);
            console.log("<<< (insert)" + JSON.stringify(todos));
            todos.push(item);
            this.setLocalStorage(todos);

            if (callback) {
                callback();
            }
        }
    }

    /**
     * Remove items from the Store based on a query.
     *
     * @param {ItemQuery} query Query matching the items to remove
     * @param {function(ItemList)|function()} [callback] Called when records matching query are removed
     */
    remove(query, callback) {
        let req = this.getReq();
        req.onloadend = () => {
            let k;
            let todos = JSON.parse(req.responseText);
            console.log("<<< (remove)" + JSON.stringify(todos));
            todos = todos.filter(todo => {
                for (k in query) {
                    if (query[k] !== todo[k]) {
                        return true;
                    }
                }
                return false;
            });

            this.setLocalStorage(todos);

            if (callback) {
                callback(todos);
            }
        }
    }

    /**
     * Count total, active, and completed todos.
     *
     * @param {function(number, number, number)} callback Called when the count is completed
     */
    count(callback) {
        this.find(emptyItemQuery, data => {
            const total = data.length;

            let i = total;
            let completed = 0;

            while (i--) {
                completed += data[i].completed;
            }
            callback(total, total - completed, completed);
        });
    }
}
